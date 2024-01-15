package com.sulog.api.config;

import com.sulog.api.config.data.UserSession;
import com.sulog.api.domain.session.Session;
import com.sulog.api.exception.UnauthorizedException;
import com.sulog.api.repository.session.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {
    private final SessionRepository sessionRepository;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);

    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if(request == null) {
            throw new UnauthorizedException();
        }

        Cookie[] cookies = request.getCookies();
        if (cookies.length == 0) {
            throw new UnauthorizedException();
        }

        String accessToken = Arrays.stream(cookies)
                .filter(cookie -> "SESSION".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(UnauthorizedException::new);

        // DB 사용자 확인 작업
        Session session = sessionRepository.findByAccessToken(accessToken)
                .orElseThrow(UnauthorizedException::new);

        return new UserSession(session.getUser().getId());
    }
}
