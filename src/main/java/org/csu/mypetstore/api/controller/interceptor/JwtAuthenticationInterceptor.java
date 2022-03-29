package org.csu.mypetstore.api.controller.interceptor;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.csu.mypetstore.api.annotation.PassToken;
import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.util.JWTUtil;
import org.csu.mypetstore.api.common.ResponseCode;
import org.csu.mypetstore.api.service.AccountService;
import org.csu.mypetstore.api.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

@Component
public class JwtAuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private AccountService accountService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (method.isAnnotationPresent(PassToken.class)) {
            if (method.getAnnotation(PassToken.class).required()) {
                return true;
            }
        }

//        System.out.println("check jwt");
        if (token == null) {
            System.out.println("token is null");
            generateResponse("token is null", response);
            return false;
        }
        System.out.println(token.replace("Bearer ", ""));
        String username;
        try {
            username = JWTUtil.getUsername(token.replace("Bearer ", ""));
        } catch (TokenExpiredException e) {
            response.getWriter().write(new ObjectMapper().writeValueAsString(
                    CommonResponse.createForError(ResponseCode.NEED_LOGIN.getCode(), "Token Expired"))
            );
            return false;
        }
        AccountVO account = accountService.getAccount(username);
        if (account == null) {
            generateResponse("token is invalid", response);
            return false;
        }

        request.setAttribute("account", account);
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

    private void generateResponse(String msg, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        CommonResponse<String> commonResponse = CommonResponse.createForError(msg);
        response.getWriter().write(new ObjectMapper().writeValueAsString(commonResponse));
    }
}
