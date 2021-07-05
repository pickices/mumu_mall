package com.liuxinchi.mumu_mall.controller;

import com.liuxinchi.mumu_mall.common.ApiRestResponse;
import com.liuxinchi.mumu_mall.exception.MumuMallException;
import com.liuxinchi.mumu_mall.interceptor.ConsumerInterceptor;
import com.liuxinchi.mumu_mall.model.vo.CartVO;
import com.liuxinchi.mumu_mall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping("/list")
    public ApiRestResponse list(){
        List<CartVO> cartVOList = cartService.list(ConsumerInterceptor.getCureentUser().getId());
        return ApiRestResponse.success(cartVOList);
    }

    @PostMapping("/add")
    public ApiRestResponse add(Integer productId, Integer count) throws MumuMallException {
        List<CartVO> cartVOList = cartService.add(ConsumerInterceptor.getCureentUser().getId(), productId, count);
        return ApiRestResponse.success(cartVOList);
    }

    @PostMapping("/update")
    public ApiRestResponse update(Integer productId, Integer count) throws MumuMallException {
        List<CartVO> cartVOList = cartService.update(ConsumerInterceptor.getCureentUser().getId(), productId, count);
        return ApiRestResponse.success(cartVOList);
    }

    @PostMapping("/delete")
    public ApiRestResponse delete(Integer productId) throws MumuMallException {
        List<CartVO> cartVOList = cartService.delete(ConsumerInterceptor.getCureentUser().getId(), productId);
        return ApiRestResponse.success(cartVOList);
    }

    @PostMapping("/select")
    public ApiRestResponse select(Integer productId, Integer selected) throws MumuMallException {
        List<CartVO> cartVOList = cartService.updateSelectOrNot(ConsumerInterceptor.getCureentUser().getId(), productId,selected);
        return ApiRestResponse.success(cartVOList);
    }

    @PostMapping("/selectAll")
    public ApiRestResponse selectAll(Integer selected) throws MumuMallException {
        List<CartVO> cartVOList = cartService.updateSelectOrNotAll(ConsumerInterceptor.getCureentUser().getId(),selected);
        return ApiRestResponse.success(cartVOList);
    }
}
