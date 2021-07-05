package com.liuxinchi.mumu_mall.controller;

import com.github.pagehelper.PageInfo;
import com.liuxinchi.mumu_mall.common.ApiRestResponse;
import com.liuxinchi.mumu_mall.exception.MumuMallException;
import com.liuxinchi.mumu_mall.model.pojo.Product;
import com.liuxinchi.mumu_mall.model.request.ProductListForConsumerReq;
import com.liuxinchi.mumu_mall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductConsumerController {

    @Autowired
    ProductService productService;

    @ResponseBody
    @GetMapping("/product/detail")
    public ApiRestResponse detail(Integer id){
        Product detail = productService.getDetail(id);
        return ApiRestResponse.success(detail);
    }

    @ResponseBody
    @GetMapping("/product/list")
    public ApiRestResponse listForConsumer(ProductListForConsumerReq productListForConsumerReq) {
        PageInfo pageInfo = productService.listForConsumer(productListForConsumerReq);
        return ApiRestResponse.success(pageInfo);
    }
}
