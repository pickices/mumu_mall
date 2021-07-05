package com.liuxinchi.mumu_mall.interceptor;

import com.liuxinchi.mumu_mall.common.Constant;
import com.liuxinchi.mumu_mall.model.pojo.User;
import com.liuxinchi.mumu_mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@Component
public class ConsumerInterceptor implements HandlerInterceptor {

    private static User cureentUser;

    public static User getCureentUser() {
        return cureentUser;
    }

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        cureentUser = (User) session.getAttribute(Constant.MUMU_MALL_USER);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        if(cureentUser==null){
            PrintWriter printWriter = response.getWriter();

            printWriter.write("{\n" +
                    "  \"status\": 10007,\n" +
                    "  \"msg\": \"用户未登录\",\n" +
                    "  \"data\": null\n" +
                    "}");
            printWriter.flush();
            printWriter.close();
            return false;
        }
        return true;
    }
}
