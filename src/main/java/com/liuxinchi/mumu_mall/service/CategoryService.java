package com.liuxinchi.mumu_mall.service;

import com.github.pagehelper.PageInfo;
import com.liuxinchi.mumu_mall.exception.MumuMallException;
import com.liuxinchi.mumu_mall.model.request.AddCatgoryReq;
import com.liuxinchi.mumu_mall.model.request.UpdateCatgoryReq;
import com.liuxinchi.mumu_mall.model.vo.CategoryVO;

import java.util.List;

public interface CategoryService {
    void addCategory(AddCatgoryReq addCatgoryReq) throws MumuMallException;

    void updateCategory(UpdateCatgoryReq updateCatgoryReq) throws MumuMallException;

    void deleteCategory(Integer id) throws MumuMallException;

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    List<CategoryVO> listForConsumer(Integer parentId);
}
