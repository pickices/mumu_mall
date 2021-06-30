package com.liuxinchi.mumu_mall.service.impl;

import com.liuxinchi.mumu_mall.exception.MumuMallException;
import com.liuxinchi.mumu_mall.exception.MumuMallExceptionEnum;
import com.liuxinchi.mumu_mall.model.dao.CategoryMapper;
import com.liuxinchi.mumu_mall.model.dao.ProductMapper;
import com.liuxinchi.mumu_mall.model.pojo.Product;
import com.liuxinchi.mumu_mall.model.request.AddProductReq;
import com.liuxinchi.mumu_mall.model.request.UpdateProductReq;
import com.liuxinchi.mumu_mall.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public void addProduct(AddProductReq addProductReq) throws MumuMallException {
        if(productMapper.selectByName(addProductReq.getName())!=null){
            throw new MumuMallException(MumuMallExceptionEnum.NAME_EXISTED);
        }

        if(categoryMapper.selectByPrimaryKey(addProductReq.getCategoryId())==null){
            throw new MumuMallException(MumuMallExceptionEnum.CATGORY_NOT_EXISTD);
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

}
