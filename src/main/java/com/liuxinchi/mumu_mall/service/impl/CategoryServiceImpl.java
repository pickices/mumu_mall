package com.liuxinchi.mumu_mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liuxinchi.mumu_mall.exception.MumuMallException;
import com.liuxinchi.mumu_mall.exception.MumuMallExceptionEnum;
import com.liuxinchi.mumu_mall.model.dao.CategoryMapper;
import com.liuxinchi.mumu_mall.model.pojo.Category;
import com.liuxinchi.mumu_mall.model.request.AddCatgoryReq;
import com.liuxinchi.mumu_mall.model.request.UpdateCatgoryReq;
import com.liuxinchi.mumu_mall.model.vo.CategoryVO;
import com.liuxinchi.mumu_mall.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

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

        if (categoryOld!=null && !categoryOld.getId().equals(updateCatgoryReq.getId())) {
            throw new MumuMallException(MumuMallExceptionEnum.NAME_EXISTED);
        }

        Category category = new Category();
        BeanUtils.copyProperties(updateCatgoryReq,category);
        if(categoryMapper.updateByPrimaryKeySelective(category)!=1){
            throw new MumuMallException(MumuMallExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public void deleteCategory(Integer id) throws MumuMallException {
        if(categoryMapper.deleteByPrimaryKey(id)!=1){
            throw new MumuMallException(MumuMallExceptionEnum.DELETE_FAILED);
        }
    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize,"type,order_num");
        List<Category> categoryList = categoryMapper.selectList();
        PageInfo pageInfo = new PageInfo<>(categoryList);
        return pageInfo;
    }

    @Override
//    @Cacheable(value = "listForConsumer")
    public List<CategoryVO> listForConsumer(Integer parentId) {
        List<CategoryVO> categoryVOList = new ArrayList<>();
        recursiveFindCategory(categoryVOList,parentId);
        return categoryVOList;
    }

    private void recursiveFindCategory(List<CategoryVO> categoryVOList, Integer parentId){
        List<Category> categoryList = categoryMapper.selectListByParentId(parentId);
        if(!CollectionUtils.isEmpty(categoryList)){
            for (Category category : categoryList) {
                CategoryVO categoryVo = new CategoryVO();
                BeanUtils.copyProperties(category,categoryVo);
                recursiveFindCategory(categoryVo.getChildCategory(),categoryVo.getId());
                categoryVOList.add(categoryVo);
            }
        }
    }
}
