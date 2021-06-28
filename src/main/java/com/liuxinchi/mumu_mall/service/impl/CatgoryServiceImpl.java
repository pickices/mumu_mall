package com.liuxinchi.mumu_mall.service.impl;

import com.liuxinchi.mumu_mall.exception.MumuMallException;
import com.liuxinchi.mumu_mall.exception.MumuMallExceptionEnum;
import com.liuxinchi.mumu_mall.model.dao.CategoryMapper;
import com.liuxinchi.mumu_mall.model.pojo.Category;
import com.liuxinchi.mumu_mall.model.request.AddCatgoryReq;
import com.liuxinchi.mumu_mall.model.request.UpdateCatgoryReq;
import com.liuxinchi.mumu_mall.service.CatgoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatgoryServiceImpl implements CatgoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public void addCategory(AddCatgoryReq addCatgoryReq) throws MumuMallException {
        if (categoryMapper.selectByName(addCatgoryReq.getName())!=null) {
            throw new MumuMallException(MumuMallExceptionEnum.NAME_EXISTED);
        }

        Category category = new Category();
        BeanUtils.copyProperties(addCatgoryReq,category);
        if(categoryMapper.insertSelective(category)!=1){
            throw new MumuMallException(MumuMallExceptionEnum.INSERT_FAILED);
        }
    }

    @Override
    public void updateCategory(UpdateCatgoryReq updateCatgoryReq) throws MumuMallException {

        Category categoryOld = categoryMapper.selectByName(updateCatgoryReq.getName());

        if (categoryOld!=null && categoryOld.getId()!=updateCatgoryReq.getId()) {
            throw new MumuMallException(MumuMallExceptionEnum.NAME_EXISTED);
        }

        Category category = new Category();
        BeanUtils.copyProperties(updateCatgoryReq,category);
        if(categoryMapper.updateByPrimaryKeySelective(category)!=1){
            throw new MumuMallException(MumuMallExceptionEnum.UPDATE_FAILED);
        }
    }
}
