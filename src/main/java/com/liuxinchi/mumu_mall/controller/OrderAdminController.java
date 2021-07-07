package com.liuxinchi.mumu_mall.controller;

import com.github.pagehelper.PageInfo;
import com.liuxinchi.mumu_mall.common.ApiRestResponse;
import com.liuxinchi.mumu_mall.exception.MumuMallException;
import com.liuxinchi.mumu_mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/order")
public class OrderAdminController {

    @Autowired
    OrderService orderService;

    @GetMapping("/list")
    public ApiRestResponse listForAdmin(@RequestParam Integer pageNum, @RequestParam Integer pageSize) throws MumuMallException {
        PageInfo pageInfo = orderService.listForAdmin(pageNum,pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @PostMapping("/delivered")
    public ApiRestResponse delivered(@RequestParam String orderNo) throws MumuMallException {
        orderService.delivered(orderNo);
        return ApiRestResponse.success();
    }

}
