package com.liuxinchi.mumu_mall.service;

import com.liuxinchi.mumu_mall.exception.MumuMallException;
import com.liuxinchi.mumu_mall.model.vo.CartVO;

import java.util.List;

public interface CartService {
    List<CartVO> list(Integer userId);

    List<CartVO> add(Integer userId, Integer productId, Integer count) throws MumuMallException;

    List<CartVO> update(Integer userId, Integer productId, Integer count) throws MumuMallException;

    List<CartVO> delete(Integer userId, Integer productId) throws MumuMallException;

    List<CartVO> updateSelectOrNot(Integer userId, Integer productId, Integer selected) throws MumuMallException;

    List<CartVO> updateSelectOrNotAll(Integer userId, Integer selected) throws MumuMallException;
}
