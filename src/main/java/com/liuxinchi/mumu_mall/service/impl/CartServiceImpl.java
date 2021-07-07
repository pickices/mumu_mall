package com.liuxinchi.mumu_mall.service.impl;

import com.liuxinchi.mumu_mall.common.Constant;
import com.liuxinchi.mumu_mall.exception.MumuMallException;
import com.liuxinchi.mumu_mall.exception.MumuMallExceptionEnum;
import com.liuxinchi.mumu_mall.model.dao.CartMapper;
import com.liuxinchi.mumu_mall.model.dao.ProductMapper;
import com.liuxinchi.mumu_mall.model.pojo.Cart;
import com.liuxinchi.mumu_mall.model.pojo.Product;
import com.liuxinchi.mumu_mall.model.vo.CartVO;
import com.liuxinchi.mumu_mall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CartMapper cartMapper;

    @Override
    public List<CartVO> list(Integer userId){
        List<CartVO> cartVOList = cartMapper.selectList(userId);
        for (CartVO cartVO : cartVOList) {
            cartVO.setTotalPrice(cartVO.getUnitPrice()*cartVO.getQuantity());
        }
        return cartVOList;
    }

    @Override
    public List<CartVO> add(Integer userId, Integer productId, Integer count) throws MumuMallException {
        Cart cart = cartMapper.selectByUserIdAndProductId(userId, productId);

        if(cart==null){
            validProduct(productId,count);
            //购物车内没有这件商品，需要新添加一条记录
            cart = new Cart();
            cart.setUserId(userId);
            cart.setProductId(productId);
            cart.setQuantity(count);
            cart.setSelected(Constant.SelectedStatus.SELECTED);
            cartMapper.insertSelective(cart);
        }else{
            validProduct(productId,count+cart.getQuantity());
            //购物车已有这件商品，只需要改变商品数量
            cart.setQuantity(cart.getQuantity()+count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return list(userId);
    }

    //商品可买性校验
    private void validProduct(Integer productId, Integer count) throws MumuMallException {
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product==null || product.getStatus().equals(Constant.SellStatus.NOT_SELL)){
            throw new MumuMallException(MumuMallExceptionEnum.NOT_SALE);
        }

        if(product.getStock()<count){
            throw new MumuMallException(MumuMallExceptionEnum.NOT_ENOUGH);
        }
    }

    @Override
    public List<CartVO> update(Integer userId, Integer productId, Integer count) throws MumuMallException {
        Cart cart = cartMapper.selectByUserIdAndProductId(userId, productId);

        if(cart==null){
            //购物车没有这件商品，更新失败
            throw new MumuMallException(MumuMallExceptionEnum.UPDATE_FAILED);
        }else{
            //购物车没有这件商品，更新前先进行商品可买性校验
            validProduct(productId,count);
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return list(userId);
    }

    @Override
    public List<CartVO> delete(Integer userId, Integer productId) throws MumuMallException {
        Cart cart = cartMapper.selectByUserIdAndProductId(userId, productId);

        if(cart==null){
            throw new MumuMallException(MumuMallExceptionEnum.DELETE_FAILED);
        }else{
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
        return list(userId);
    }

    @Override
    public List<CartVO> updateSelectOrNot(Integer userId, Integer productId, Integer selected) throws MumuMallException {
        Cart cart = cartMapper.selectByUserIdAndProductId(userId, productId);

        if(cart==null){
            throw new MumuMallException(MumuMallExceptionEnum.UPDATE_FAILED);
        }else{
            cartMapper.updateSelectOrNot(userId,productId,selected);
        }
        return list(userId);
    }

    @Override
    public List<CartVO> updateSelectOrNotAll(Integer userId, Integer selected) throws MumuMallException {
        if(cartMapper.updateSelectOrNot(userId, null, selected)==0){
            throw new MumuMallException(MumuMallExceptionEnum.UPDATE_FAILED);
        }
        return list(userId);
    }
}
