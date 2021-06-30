package com.liuxinchi.mumu_mall.controller;

import com.github.pagehelper.PageInfo;
import com.liuxinchi.mumu_mall.common.ApiRestResponse;
import com.liuxinchi.mumu_mall.exception.MumuMallException;
import com.liuxinchi.mumu_mall.model.request.AddCatgoryReq;
import com.liuxinchi.mumu_mall.model.request.UpdateCatgoryReq;
import com.liuxinchi.mumu_mall.model.vo.CategoryVo;
import com.liuxinchi.mumu_mall.service.CatgoryService;
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
public class CatgoryController {

    @Autowired
    UserService userService;

    @Autowired
    CatgoryService catgoryService;

    @ResponseBody
    @PostMapping("/admin/category/add")
    public ApiRestResponse addCatgory(HttpSession httpSession, @Valid @RequestBody AddCatgoryReq addCatgoryReq) throws MumuMallException {

        catgoryService.addCategory(addCatgoryReq);
        return ApiRestResponse.success();
    }

    @ApiOperation("更新类别")
    @ResponseBody
    @PostMapping("/admin/category/update")
    public ApiRestResponse updateCatgory(HttpSession httpSession, @Valid @RequestBody UpdateCatgoryReq updateCatgoryReq) throws MumuMallException {

        catgoryService.updateCategory(updateCatgoryReq);
        return ApiRestResponse.success();
    }

    @ApiOperation("删除类别")
    @ResponseBody
    @PostMapping("/admin/category/delete")
    public ApiRestResponse deleteCatgory(@RequestParam("id") Integer id) throws MumuMallException {

        catgoryService.deleteCatgory(id);
        return ApiRestResponse.success();
    }

    @ApiOperation("后台展示列表")
    @ResponseBody
    @GetMapping("/admin/category/list")
    public ApiRestResponse listForAdmin(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) throws MumuMallException {
        PageInfo pageInfo = catgoryService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @ApiOperation("前台展示列表")
    @ResponseBody
    @GetMapping("/category/list")
    public ApiRestResponse listForConsumer() throws MumuMallException {
        List<CategoryVo> categoryVoList = catgoryService.listForConsumer();
        return ApiRestResponse.success(categoryVoList);
    }
}
