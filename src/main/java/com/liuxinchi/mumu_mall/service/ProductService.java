package com.liuxinchi.mumu_mall.service;

import com.github.pagehelper.PageInfo;
import com.liuxinchi.mumu_mall.exception.MumuMallException;
import com.liuxinchi.mumu_mall.model.request.AddProductReq;
import com.liuxinchi.mumu_mall.model.request.UpdateProductReq;

public interface ProductService {
    void addProduct(AddProductReq addProductReq) throws MumuMallException;

    void updateProduct(UpdateProductReq updateProductReq) throws MumuMallException;

    void deleteProduct(Integer id) throws MumuMallException;

    void batchUpdateSellStatus(Integer[] ids, Integer sellStatus) throws MumuMallException;

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);
}
