package com.liuxinchi.mumu_mall.service;

import com.github.pagehelper.PageInfo;
import com.liuxinchi.mumu_mall.exception.MumuMallException;
import com.liuxinchi.mumu_mall.model.request.CreatOrderReq;
import com.liuxinchi.mumu_mall.model.vo.OrderVO;

public interface OrderService {
    String create(CreatOrderReq creatOrderReq) throws MumuMallException;

    OrderVO detail(String orderNo) throws MumuMallException;

    PageInfo<OrderVO> listForConsumer(Integer pageNum, Integer pageSize) throws MumuMallException;

    void cancel(String orderNo) throws MumuMallException;

    String generateQRCode(String orderNo);

    PageInfo<OrderVO> listForAdmin(Integer pageNum, Integer pageSize) throws MumuMallException;

    void pay(String orderNo) throws MumuMallException;

    void delivered(String orderNo) throws MumuMallException;

    void finish(String orderNo) throws MumuMallException;
}
