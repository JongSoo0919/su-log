package com.sulog.api.config;

import com.sulog.api.config.data.UserSession;
import com.sulog.api.domain.session.Session;
import com.sulog.api.exception.UnauthorizedException;
import com.sulog.api.repository.session.SessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
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
@Slf4j
public class AuthResolver implements HandlerMethodArgumentResolver {
    private final SessionRepository sessionRepository;
    public final static String KEY = "Ra9MeLVbrVoRKwlyRh8S7B11dCNLPnqM16/ycg5BIiE=";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);

    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String jws = webRequest.getHeader("Authorization");
        if(jws == null || jws.isEmpty()) {
            throw new UnauthorizedException();
        }

        byte[] decodedKey = Base64.decodeBase64(KEY);

        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(decodedKey)
                    .build()
                    .parseSignedClaims(jws);

            log.info(">>>>> claims {}",claims.toString());
            String userId = claims.getBody().getSubject();
            return new UserSession(Long.parseLong(userId));
            //OK, we can trust this JWT
        } catch (JwtException e) {
            //don't trust the JWT!
        }

        return null;
//        return new UserSession(session.getUser().getId());
    }
}
