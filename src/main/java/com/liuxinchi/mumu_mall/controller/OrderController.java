package com.liuxinchi.mumu_mall.controller;

import com.github.pagehelper.PageInfo;
import com.liuxinchi.mumu_mall.common.ApiRestResponse;
import com.liuxinchi.mumu_mall.exception.MumuMallException;
import com.liuxinchi.mumu_mall.model.request.CreatOrderReq;
import com.liuxinchi.mumu_mall.model.vo.OrderVO;
import com.liuxinchi.mumu_mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/create ")
    public ApiRestResponse create(@RequestBody CreatOrderReq creatOrderReq) throws MumuMallException {
        String orderNo = orderService.create(creatOrderReq);
        return ApiRestResponse.success(orderNo);
    }

    @GetMapping("/detail")
    public ApiRestResponse detail(@RequestParam String orderNo) throws MumuMallException {
        OrderVO orderVO = orderService.detail(orderNo);
        return ApiRestResponse.success(orderVO);
    }

    @GetMapping("list")
    public ApiRestResponse listForConsumer(@RequestParam Integer pageNum,@RequestParam Integer pageSize) throws MumuMallException {
        PageInfo pageInfo = orderService.listForConsumer(pageNum,pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @PostMapping("/cancel")
    public ApiRestResponse cancel(@RequestParam String orderNo) throws MumuMallException {
        orderService.cancel(orderNo);
        return ApiRestResponse.success();
    }

    @PostMapping("/qrcode")
    public ApiRestResponse qrcode(@RequestParam String orderNo){
        String qrcodeImg = orderService.generateQRCode(orderNo);
        return ApiRestResponse.success(qrcodeImg);
    }

    @PostMapping("/pay")
    public ApiRestResponse pay(@RequestParam String orderNo) throws MumuMallException {
        orderService.pay(orderNo);
        return ApiRestResponse.success();
    }

    @PostMapping("/finish")
    public ApiRestResponse finish(@RequestParam String orderNo) throws MumuMallException {
        orderService.finish(orderNo);
        return ApiRestResponse.success();
    }

}
