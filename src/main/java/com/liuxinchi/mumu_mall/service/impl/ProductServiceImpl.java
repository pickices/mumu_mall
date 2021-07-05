package com.liuxinchi.mumu_mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liuxinchi.mumu_mall.exception.MumuMallException;
import com.liuxinchi.mumu_mall.exception.MumuMallExceptionEnum;
import com.liuxinchi.mumu_mall.model.dao.CategoryMapper;
import com.liuxinchi.mumu_mall.model.dao.ProductMapper;
import com.liuxinchi.mumu_mall.model.pojo.Product;
import com.liuxinchi.mumu_mall.model.query.ProductListForConsumerQuery;
import com.liuxinchi.mumu_mall.model.request.AddProductReq;
import com.liuxinchi.mumu_mall.model.request.ProductListForConsumerReq;
import com.liuxinchi.mumu_mall.model.request.UpdateProductReq;
import com.liuxinchi.mumu_mall.model.vo.CategoryVO;
import com.liuxinchi.mumu_mall.service.CategoryService;
import com.liuxinchi.mumu_mall.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    CategoryService categoryService;

    @Override
    public void addProduct(AddProductReq addProductReq) throws MumuMallException {
        if(productMapper.selectByName(addProductReq.getName())!=null){
            throw new MumuMallException(MumuMallExceptionEnum.NAME_EXISTED);
        }

        if(categoryMapper.selectByPrimaryKey(addProductReq.getCategoryId())==null){
            throw new MumuMallException(MumuMallExceptionEnum.CATEGORY_NOT_EXISTED);
        }

        Product product = new Product();
        BeanUtils.copyProperties(addProductReq,product);
        if(productMapper.insertSelective(product)!=1){
            throw new MumuMallException(MumuMallExceptionEnum.INSERT_FAILED);
        }
    }

    @Override
    public void updateProduct(UpdateProductReq updateProductReq) throws MumuMallException {

        Product oldProduct = productMapper.selectByName(updateProductReq.getName());

        if (oldProduct!=null && !updateProductReq.getId().equals(oldProduct.getId())) {
            throw new MumuMallException(MumuMallExceptionEnum.NAME_EXISTED);
        }

        Product newProduct = new Product();
        BeanUtils.copyProperties(updateProductReq, newProduct);

        if(productMapper.updateByPrimaryKeySelective(newProduct)!=1){
            throw new MumuMallException(MumuMallExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public void deleteProduct(Integer id) throws MumuMallException {
        if(productMapper.deleteByPrimaryKey(id)!=1){
            throw new MumuMallException(MumuMallExceptionEnum.DELETE_FAILED);
        }
    }

    @Override
    public void batchUpdateSellStatus(Integer[] ids, Integer sellStatus) throws MumuMallException {
        if(productMapper.batchUpdateSellStatus(ids,sellStatus)!=ids.length){
            throw new MumuMallException(MumuMallExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Product> products = productMapper.selectListForAdmin();
        PageInfo pageInfo = new PageInfo(products);
        return pageInfo;
    }

    @Override
    public PageInfo listForConsumer(ProductListForConsumerReq productListForConsumerReq) {

        ProductListForConsumerQuery productListForConsumerQuery = new ProductListForConsumerQuery();

        productListForConsumerQuery.setKeyword(productListForConsumerReq.getKeyword());

        if(productListForConsumerReq.getCategoryId()!=null){
            List<CategoryVO> categoryVOList = categoryService.listForConsumer(productListForConsumerReq.getCategoryId());
            List<Integer> categoryIds = new ArrayList<>();
            categoryIds.add(productListForConsumerReq.getCategoryId());
            getCategoryIds(categoryVOList,categoryIds);
            productListForConsumerQuery.setCategoryIds(categoryIds);
        }

        if(StringUtils.isEmpty(productListForConsumerReq.getOrderBy())){
            PageHelper.startPage(productListForConsumerReq.getPageNum(),productListForConsumerReq.getPageSize(),"update_time desc");
        }else{
            PageHelper.startPage(productListForConsumerReq.getPageNum(),productListForConsumerReq.getPageSize(),productListForConsumerReq.getOrderBy()+",update_time desc");
        }

        List<Product> productList = productMapper.selectListForConsumer(productListForConsumerQuery);

        PageInfo pageInfo = new PageInfo(productList);
        return pageInfo;

    }

    private void getCategoryIds(List<CategoryVO> categoryVOList, List<Integer> categoryIds){
        if(!CollectionUtils.isEmpty(categoryVOList)){
            for (CategoryVO categoryVo : categoryVOList) {
                categoryIds.add(categoryVo.getId());
                getCategoryIds(categoryVo.getChildCategory(),categoryIds);
            }
        }
    }

    @Override
    public Product getDetail(Integer id){
        return productMapper.selectByPrimaryKey(id);
    }

}
