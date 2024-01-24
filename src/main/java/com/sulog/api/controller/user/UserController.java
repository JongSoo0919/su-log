package com.sulog.api.controller.user;

import com.sulog.api.config.AuthResolver;
import com.sulog.api.domain.user.Users;
import com.sulog.api.exception.InvalidException;
import com.sulog.api.exception.InvalidSigninInformation;
import com.sulog.api.model.user.request.LoginRequestDto;
import com.sulog.api.model.user.response.SessionResponse;
import com.sulog.api.repository.user.UserRepository;
import com.sulog.api.service.user.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    @Value("${jwt.secret.key}")
    private String secretKey;
    @PostMapping("/auth/login")
    public ResponseEntity<SessionResponse> login(@RequestBody LoginRequestDto loginRequestDto) {
        log.info(">>> login = {}",loginRequestDto);
        Long userId = userService.signIn(loginRequestDto);
//        ResponseCookie cookie = ResponseCookie.from("SESSION", accessToken)
//                .domain("localhost")
//                .path("/")
//                .httpOnly(false)
//                .secure(false)
//                .maxAge(Duration.ofDays(30))
//                .sameSite("Strict")
//                .build();
//
//        log.info(">>>>>>>>>> cookie = {}",cookie.toString());

        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));

        String jws = Jwts.builder()
                .subject(String.valueOf(userId))
                .issuedAt(new Date())
                .expiration(new Date())
                .signWith(key)
                .compact();

        return ResponseEntity.ok(new SessionResponse(jws));
    }
}

