package com.liuxinchi.mumu_mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liuxinchi.mumu_mall.exception.MumuMallException;
import com.liuxinchi.mumu_mall.exception.MumuMallExceptionEnum;
import com.liuxinchi.mumu_mall.model.dao.CategoryMapper;
import com.liuxinchi.mumu_mall.model.pojo.Category;
import com.liuxinchi.mumu_mall.model.request.AddCatgoryReq;
import com.liuxinchi.mumu_mall.model.request.UpdateCatgoryReq;
import com.liuxinchi.mumu_mall.model.vo.CategoryVo;
import com.liuxinchi.mumu_mall.service.CatgoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void deleteCatgory(Integer id) throws MumuMallException {
        if(categoryMapper.deleteByPrimaryKey(id)!=1){
            throw new MumuMallException(MumuMallExceptionEnum.DELETE_FAILED);
        }
    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize) throws MumuMallException {
        PageHelper.startPage(pageNum,pageSize,"type,order_num");
        List<Category> categoryList = categoryMapper.selectList();
        PageInfo pageInfo = new PageInfo(categoryList);
        return pageInfo;
    }

    @Override
    @Cacheable(value = "listForConsumer")
    public List<CategoryVo> listForConsumer() throws MumuMallException {
        List<CategoryVo> categoryVoList = new ArrayList<>();
        recursiveFindCategory(categoryVoList,0);
        return categoryVoList;
    }

    private void recursiveFindCategory(List<CategoryVo> categoryVoList, Integer parentId){
        List<Category> categoryList = categoryMapper.selectListByParentId(parentId);
        if(!CollectionUtils.isEmpty(categoryList)){
            for (Category category : categoryList) {
                CategoryVo categoryVo = new CategoryVo();
                BeanUtils.copyProperties(category,categoryVo);
                recursiveFindCategory(categoryVo.getChildCategory(),categoryVo.getId());
                categoryVoList.add(categoryVo);
            }
        }
    }
}
