package com.liuxinchi.mumu_mall.controller;

import com.github.pagehelper.PageInfo;
import com.liuxinchi.mumu_mall.common.ApiRestResponse;
import com.liuxinchi.mumu_mall.exception.MumuMallException;
import com.liuxinchi.mumu_mall.model.request.AddCatgoryReq;
import com.liuxinchi.mumu_mall.model.request.UpdateCatgoryReq;
import com.liuxinchi.mumu_mall.model.vo.CategoryVO;
import com.liuxinchi.mumu_mall.service.CategoryService;
import com.liuxinchi.mumu_mall.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * @author 拾荒老冰棍
 */
@Controller
public class CategoryController {

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @ResponseBody
    @PostMapping("/admin/category/add")
    public ApiRestResponse addCategory(@Valid @RequestBody AddCatgoryReq addCatgoryReq) throws MumuMallException {

        categoryService.addCategory(addCatgoryReq);
        return ApiRestResponse.success();
    }

    @ApiOperation("更新类别")
    @ResponseBody
    @PostMapping("/admin/category/update")
    public ApiRestResponse updateCategory(@Valid @RequestBody UpdateCatgoryReq updateCatgoryReq) throws MumuMallException {

        categoryService.updateCategory(updateCatgoryReq);
        return ApiRestResponse.success();
    }

    @ApiOperation("删除类别")
    @ResponseBody
    @PostMapping("/admin/category/delete")
    public ApiRestResponse deleteCategory(@RequestParam("id") Integer id) throws MumuMallException {

        categoryService.deleteCategory(id);
        return ApiRestResponse.success();
    }

    @ApiOperation("后台展示列表")
    @ResponseBody
    @GetMapping("/admin/category/list")
    public ApiRestResponse listForAdmin(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize){
        PageInfo pageInfo = categoryService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @ApiOperation("前台展示列表")
    @ResponseBody
    @GetMapping("/category/list")
    public ApiRestResponse listForConsumer(){
        List<CategoryVO> categoryVOList = categoryService.listForConsumer(0);
        return ApiRestResponse.success(categoryVOList);
    }
}
