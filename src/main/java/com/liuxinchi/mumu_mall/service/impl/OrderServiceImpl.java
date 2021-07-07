package com.liuxinchi.mumu_mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.zxing.WriterException;
import com.liuxinchi.mumu_mall.common.Constant;
import com.liuxinchi.mumu_mall.exception.MumuMallException;
import com.liuxinchi.mumu_mall.exception.MumuMallExceptionEnum;
import com.liuxinchi.mumu_mall.interceptor.ConsumerInterceptor;
import com.liuxinchi.mumu_mall.model.dao.CartMapper;
import com.liuxinchi.mumu_mall.model.dao.OrderItemMapper;
import com.liuxinchi.mumu_mall.model.dao.OrderMapper;
import com.liuxinchi.mumu_mall.model.dao.ProductMapper;
import com.liuxinchi.mumu_mall.model.pojo.Order;
import com.liuxinchi.mumu_mall.model.pojo.OrderItem;
import com.liuxinchi.mumu_mall.model.pojo.Product;
import com.liuxinchi.mumu_mall.model.request.CreatOrderReq;
import com.liuxinchi.mumu_mall.model.vo.CartVO;
import com.liuxinchi.mumu_mall.model.vo.OrderItemVO;
import com.liuxinchi.mumu_mall.model.vo.OrderVO;
import com.liuxinchi.mumu_mall.service.CartService;
import com.liuxinchi.mumu_mall.service.OrderService;
import com.liuxinchi.mumu_mall.service.UserService;
import com.liuxinchi.mumu_mall.util.OrderCodeFactory;
import com.liuxinchi.mumu_mall.util.QRCodeGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    CartService cartService;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    UserService userService;

    @Value("${file.upload.ip}")
    String ip;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(CreatOrderReq creatOrderReq) throws MumuMallException {
        int userId = ConsumerInterceptor.getCureentUser().getId();
        //查询用户购物车的所有商品
        List<CartVO> cartVOList = cartService.list(userId);
        //查询购物车中勾选的商品
        List<CartVO> cartVOSelectedList = new ArrayList<>();
        for (CartVO cartVO : cartVOList) {
            if(cartVO.getSelected().equals(Constant.SelectedStatus.SELECTED)){
                cartVOSelectedList.add(cartVO);
            }
        }

        //如果没有选中的商品，返回异常
        if(CollectionUtils.isEmpty(cartVOSelectedList)){
            throw new MumuMallException(MumuMallExceptionEnum.CART_EMPTY);
        }

        //判断商品可买性
        validProduct(cartVOSelectedList);

        //把购物车对象转换为订单item对象
        List<OrderItem> orderItemList = cartVOListToOrderItemList(cartVOSelectedList);

        //扣库存
        for (CartVO cartVO : cartVOSelectedList) {
            Product product = productMapper.selectByPrimaryKey(cartVO.getProductId());
            if(product.getStock()-cartVO.getQuantity()<0){
                throw new MumuMallException(MumuMallExceptionEnum.NOT_ENOUGH);
            }
            product.setStock(product.getStock()-cartVO.getQuantity());
            productMapper.updateByPrimaryKeySelective(product);
        }

        //把购物车中已勾选的商品删除
        deleteCart(cartVOSelectedList);

        //生成订单对象
        Order order = new Order();
        //生成订单号
        order.setOrderNo(OrderCodeFactory.getOrderCode(Long.valueOf(userId)));
        //完善order对象，并插入到表中
        order.setUserId(userId);
        order.setTotalPrice(calTotalPrice(orderItemList));
        order.setOrderStatus(Constant.OrderStatusEnum.NOT_PAID.getCode());
        order.setReceiverAddress(creatOrderReq.getReceiverAddress());
        order.setReceiverMobile(creatOrderReq.getReceiverMobile());
        order.setReceiverName(creatOrderReq.getReceiverName());
        order.setPaymentType(creatOrderReq.getPaymentType());
        order.setPostage(creatOrderReq.getPostage());
        orderMapper.insertSelective(order);

        //循环保存每个商品到order_item表
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderNo(order.getOrderNo());
            orderItemMapper.insert(orderItem);
        }

        return order.getOrderNo();

    }

    @Override
    public OrderVO detail(String orderNo) throws MumuMallException {
        Order order = orderMapper.selectByOrderNo(orderNo);
        //判断订单是否存在
        if(order==null){
            throw new MumuMallException(MumuMallExceptionEnum.NO_ORDER);
        }
        //判断订单是否属于该用户
        if(!order.getUserId().equals(ConsumerInterceptor.getCureentUser().getId())){
            throw new MumuMallException(MumuMallExceptionEnum.NOT_YOUR_ORDER);
        }

        return getOrderVO(order);
    }

    @Override
    public PageInfo listForConsumer(Integer pageNum, Integer pageSize) throws MumuMallException {
        Integer userId = ConsumerInterceptor.getCureentUser().getId();
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orderList = orderMapper.selectListByUerId(userId);

        //构造orderVOList
        List<OrderVO> orderVOList = getOrderVOList(orderList);

        PageInfo pageInfo = new PageInfo(orderList);
        pageInfo.setList(orderVOList);

        return pageInfo;
    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize) throws MumuMallException {
        PageHelper.startPage(pageNum,pageSize);

        List<Order> orderList = orderMapper.selectListAll();

        //构造orderVOList
        List<OrderVO> orderVOList = getOrderVOList(orderList);

        PageInfo pageInfo = new PageInfo<>(orderList);
        pageInfo.setList(orderVOList);

        return pageInfo;
    }

    @Override
    public void pay(String orderNo) throws MumuMallException {
        Order order = orderMapper.selectByOrderNo(orderNo);
        //判断订单是否存在
        if(order==null){
            throw new MumuMallException(MumuMallExceptionEnum.NO_ORDER);
        }
        //判断订单是否属于该用户
        if(!order.getUserId().equals(ConsumerInterceptor.getCureentUser().getId())){
            throw new MumuMallException(MumuMallExceptionEnum.NOT_YOUR_ORDER);
        }
        if(!order.getOrderStatus().equals(Constant.OrderStatusEnum.NOT_PAID.getCode())){
            throw new MumuMallException(MumuMallExceptionEnum.WRONG_ORDER_STATUS);
        }
        order.setPayTime(new Date());
        order.setOrderStatus(Constant.OrderStatusEnum.PAID.getCode());
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public void delivered(String orderNo) throws MumuMallException {
        Order order = orderMapper.selectByOrderNo(orderNo);
        //判断订单是否存在，发货状态只能由管理员修改，故不用判断是否是当前用户操作
        if(order==null){
            throw new MumuMallException(MumuMallExceptionEnum.NO_ORDER);
        }
        if(!order.getOrderStatus().equals(Constant.OrderStatusEnum.PAID.getCode())){
            throw new MumuMallException(MumuMallExceptionEnum.WRONG_ORDER_STATUS);
        }
        order.setDeliveryTime(new Date());
        order.setOrderStatus(Constant.OrderStatusEnum.DELIVERED.getCode());
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public void finish(String orderNo) throws MumuMallException {
        Order order = orderMapper.selectByOrderNo(orderNo);
        //判断订单是否存在，发货状态只能由管理员修改，故不用判断是否是当前用户操作
        if(order==null){
            throw new MumuMallException(MumuMallExceptionEnum.NO_ORDER);
        }
        //判断是否是管理员账号，如果不是管理员，则需要验证是否是自己的订单
        if(!userService.checkAdmin(ConsumerInterceptor.getCureentUser()) &&
                !order.getUserId().equals(ConsumerInterceptor.getCureentUser().getId())){
            throw new MumuMallException(MumuMallExceptionEnum.NOT_YOUR_ORDER);
        }
        if(!order.getOrderStatus().equals(Constant.OrderStatusEnum.DELIVERED.getCode())){
            throw new MumuMallException(MumuMallExceptionEnum.WRONG_ORDER_STATUS);
        }
        order.setEndTime(new Date());
        order.setOrderStatus(Constant.OrderStatusEnum.FINISHED.getCode());
        orderMapper.updateByPrimaryKeySelective(order);
    }

    private List<OrderVO> getOrderVOList(List<Order> orderList) throws MumuMallException {
        List<OrderVO> orderVOList = new ArrayList<>();
        for (Order order : orderList) {
            orderVOList.add(getOrderVO(order));
        }
        return orderVOList;
    }

    @Override
    public void cancel(String orderNo) throws MumuMallException {
        Order order = orderMapper.selectByOrderNo(orderNo);
        //判断订单是否存在
        if(order==null){
            throw new MumuMallException(MumuMallExceptionEnum.NO_ORDER);
        }
        //判断订单是否属于该用户
        if(!order.getUserId().equals(ConsumerInterceptor.getCureentUser().getId())){
            throw new MumuMallException(MumuMallExceptionEnum.NOT_YOUR_ORDER);
        }
        //判断订单是否可取消，只有在未付款和未发货时可以取消
        if(!order.getOrderStatus().equals(Constant.OrderStatusEnum.NOT_PAID.getCode())&&
        !order.getOrderStatus().equals(Constant.OrderStatusEnum.PAID.getCode())){
            throw new MumuMallException(MumuMallExceptionEnum.WRONG_ORDER_STATUS);
        }
        order.setOrderStatus(Constant.OrderStatusEnum.CANCELED.getCode());
        order.setEndTime(new Date());
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public String generateQRCode(String orderNo) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        int localPort = attributes.getRequest().getLocalPort();
        //生成二维码图片的信息
        String payUrl = "http://" + ip + ":" + localPort + "/pay?orderNo=" + orderNo;
        //生成二维码图片
        String qrLocalPath = Constant.FILE_UPLOAD_DIR+orderNo+".png";
        try {
            QRCodeGenerator.generateQRCodeImage(payUrl,350,350,qrLocalPath);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String qrRemotePath = "http://" + ip + ":" + localPort + "/upload/" + orderNo+".png";
        return qrRemotePath;
    }

    private OrderVO getOrderVO(Order order) throws MumuMallException {
        //构造orderVO对象
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order,orderVO);
        orderVO.setOrderStatusName(Constant.OrderStatusEnum.codeOf(order.getOrderStatus()).getValue());

        //构造orderItemVOList对象
        List<OrderItem> orderItemList = orderItemMapper.selectListByOrderNo(order.getOrderNo());
        List<OrderItemVO> orderItemVOList = new ArrayList<>();
        for (OrderItem orderItem : orderItemList) {
            OrderItemVO orderItemVO = new OrderItemVO();
            BeanUtils.copyProperties(orderItem,orderItemVO);
            orderItemVOList.add(orderItemVO);
        }

        orderVO.setOrderItemVOList(orderItemVOList);
        return orderVO;
    }

    private Integer calTotalPrice(List<OrderItem> orderItemList) {
        Integer totalPrice = 0;
        for (OrderItem orderItem : orderItemList) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    private void deleteCart(List<CartVO> cartVOSelectedList) {
        for (CartVO cartVO : cartVOSelectedList) {
            cartMapper.deleteByPrimaryKey(cartVO.getId());
        }
    }

    //把购物车对象转换为订单item对象
    private List<OrderItem> cartVOListToOrderItemList(List<CartVO> cartVOSelectedList) {
        List<OrderItem> orderItemList = new ArrayList<>();
        for (CartVO cartVO : cartVOSelectedList) {
            OrderItem orderItem = new OrderItem();
            BeanUtils.copyProperties(cartVO,orderItem);
            orderItem.setId(null);
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }

    //商品可买性校验
    private void validProduct(List<CartVO> cartVOSelectedList) throws MumuMallException {
        for (CartVO cartVO : cartVOSelectedList) {
            Product product = productMapper.selectByPrimaryKey(cartVO.getProductId());
            if(product==null || product.getStatus().equals(Constant.SellStatus.NOT_SELL)){
                throw new MumuMallException(MumuMallExceptionEnum.NOT_SALE);
            }

            if(product.getStock()<cartVO.getQuantity()){
                throw new MumuMallException(MumuMallExceptionEnum.NOT_ENOUGH);
            }
        }
    }
}
