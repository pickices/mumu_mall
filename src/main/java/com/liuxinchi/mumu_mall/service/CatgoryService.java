package com.liuxinchi.mumu_mall.service;

import com.liuxinchi.mumu_mall.exception.MumuMallException;
import com.liuxinchi.mumu_mall.model.request.AddCatgoryReq;
import com.liuxinchi.mumu_mall.model.request.UpdateCatgoryReq;

public interface CatgoryService {
    void addCategory(AddCatgoryReq addCatgoryReq) throws MumuMallException;

    void updateCategory(UpdateCatgoryReq updateCatgoryReq) throws MumuMallException;
}
