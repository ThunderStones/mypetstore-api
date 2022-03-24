package org.csu.mypetstore.api.controller.front;

import org.csu.mypetstore.api.annotation.PassToken;
import org.csu.mypetstore.api.common.JWTUtil;
import org.csu.mypetstore.api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.logging.Handler;

@Component
public class JwtAuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    AccountService accountService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (!( handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (method.isAnnotationPresent(PassToken.class)) {
            if (method.getAnnotation(PassToken.class).required()) {
                return true;
            }
        }

        System.out.println("check jwt");
        if (token == null) {
            System.out.println("token is null");
            throw new RuntimeException("请先登录");
        }

        String username = JWTUtil.getUsername(token);
        if (accountService.getAccount(username) == null) {
            throw new RuntimeException("Invalid username");
        }

        request.setAttribute("username", username);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
