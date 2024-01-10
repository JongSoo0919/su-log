package com.sulog.api.controller.user;

import com.sulog.api.domain.user.Users;
import com.sulog.api.exception.InvalidException;
import com.sulog.api.exception.InvalidSigninInformation;
import com.sulog.api.model.user.request.LoginRequestDto;
import com.sulog.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserRepository userRepository;
    @PostMapping("/auth/login")
    public Users login(@RequestBody LoginRequestDto loginRequestDto) {
        log.info(">>> login = {}",loginRequestDto);

        return (userRepository.findByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword())
                .orElseThrow(InvalidSigninInformation::new));

    }
}

