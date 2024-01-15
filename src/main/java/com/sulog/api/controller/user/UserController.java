package com.sulog.api.controller.user;

import com.sulog.api.domain.user.Users;
import com.sulog.api.exception.InvalidException;
import com.sulog.api.exception.InvalidSigninInformation;
import com.sulog.api.model.user.request.LoginRequestDto;
import com.sulog.api.model.user.response.SessionResponse;
import com.sulog.api.repository.user.UserRepository;
import com.sulog.api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        log.info(">>> login = {}",loginRequestDto);
        String accessToken = userService.signIn(loginRequestDto);
        ResponseCookie cookie = ResponseCookie.from("SESSION", accessToken)
                .domain("localhost")
                .path("/")
                .httpOnly(false)
                .secure(false)
                .maxAge(Duration.ofDays(30))
                .sameSite("Strict")
                .build();

        log.info(">>>>>>>>>> cookie = {}",cookie.toString());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}

