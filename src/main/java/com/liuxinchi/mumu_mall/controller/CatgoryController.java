package com.liuxinchi.mumu_mall.controller;

import com.liuxinchi.mumu_mall.common.ApiRestResponse;
import com.liuxinchi.mumu_mall.common.Constant;
import com.liuxinchi.mumu_mall.exception.MumuMallException;
import com.liuxinchi.mumu_mall.exception.MumuMallExceptionEnum;
import com.liuxinchi.mumu_mall.model.pojo.User;
import com.liuxinchi.mumu_mall.model.request.AddCatgoryReq;
import com.liuxinchi.mumu_mall.model.request.UpdateCatgoryReq;
import com.liuxinchi.mumu_mall.service.CatgoryService;
import com.liuxinchi.mumu_mall.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
//        User user = (User) httpSession.getAttribute(Constant.MUMU_MALL_USER);
//        if(user==null){
//            return ApiRestResponse.error(MumuMallExceptionEnum.NEED_LOGIN);
//        }
//        if(!userService.checkAdmin(user)){
//            return ApiRestResponse.error(MumuMallExceptionEnum.NEED_ADMIN);
//        }

        catgoryService.addCategory(addCatgoryReq);

        return ApiRestResponse.success();
    }

    @ApiOperation("更新类别")
    @ResponseBody
    @PostMapping("/admin/category/update")
    public ApiRestResponse updateCatgory(HttpSession httpSession, @Valid @RequestBody UpdateCatgoryReq updateCatgoryReq) throws MumuMallException {
//        User user = (User) httpSession.getAttribute(Constant.MUMU_MALL_USER);
//        if(user==null){
//            return ApiRestResponse.error(MumuMallExceptionEnum.NEED_LOGIN);
//        }
//        if(!userService.checkAdmin(user)){
//            return ApiRestResponse.error(MumuMallExceptionEnum.NEED_ADMIN);
//        }

        catgoryService.updateCategory(updateCatgoryReq);

        return ApiRestResponse.success();
    }
}
