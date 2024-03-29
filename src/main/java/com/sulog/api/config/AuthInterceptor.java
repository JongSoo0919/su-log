package com.sulog.api.config;

import com.sulog.api.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(">>>>>>>>>>>>>>>>>>>preHandle");

//        String accessToken = request.getParameter("accessToken");
//        if("인증".equals(accessToken)){
//            return true;
//        }
//
//        throw new UnauthorizedException();

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info(">>>>>>>>>>>>>>>>>>>postHandle");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info(">>>>>>>>>>>>>>>>>>>afterCompletion");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
