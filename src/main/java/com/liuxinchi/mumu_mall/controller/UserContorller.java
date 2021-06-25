package com.liuxinchi.mumu_mall.controller;

import com.liuxinchi.mumu_mall.common.ApiRestResponse;
import com.liuxinchi.mumu_mall.common.Constant;
import com.liuxinchi.mumu_mall.exception.MumuMallException;
import com.liuxinchi.mumu_mall.exception.MumuMallExceptionEnum;
import com.liuxinchi.mumu_mall.model.pojo.User;
import com.liuxinchi.mumu_mall.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author 拾荒老冰棍
 */
@Controller
public class UserContorller {

    @Autowired
    UserService userService;

    @ResponseBody
    @PostMapping("/register")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = false),
            @ApiImplicitParam(name = "password", value = "密码", required = false)
    }
    )
    public ApiRestResponse register(@RequestParam(value = "userName",required = false) String userName, @RequestParam(value = "password",required = false) String password) throws MumuMallException {
        if(StringUtils.isEmpty(userName)){
            return ApiRestResponse.error(MumuMallExceptionEnum.NEED_USER_NAME);
        }else if(StringUtils.isEmpty(password)){
            return ApiRestResponse.error(MumuMallExceptionEnum.NEED_PASSWORD);
        }else if(password.length()<8){
            return ApiRestResponse.error(MumuMallExceptionEnum.PASSWORD_TOO_SHORT);
        }else{
            userService.register(userName, password);
            return ApiRestResponse.success();
        }
    }

    @ResponseBody
    @PostMapping("/login")
    public ApiRestResponse login(@RequestParam(value = "userName",required = false) String userName, @RequestParam(value = "password",required = false) String password, HttpSession httpSession) throws MumuMallException{
        if(StringUtils.isEmpty(userName)){
            return ApiRestResponse.error(MumuMallExceptionEnum.NEED_USER_NAME);
        }else if(StringUtils.isEmpty(password)){
            return ApiRestResponse.error(MumuMallExceptionEnum.NEED_PASSWORD);
        }else{
            User user = userService.login(userName, password);
            user.setPassword(null);
            httpSession.setAttribute(Constant.MUMU_MALL_USER,user);
            return ApiRestResponse.success(user);
        }
    }
}
