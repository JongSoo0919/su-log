package com.sulog.api.service.user;

import com.sulog.api.domain.session.Session;
import com.sulog.api.domain.user.Users;
import com.sulog.api.exception.InvalidSigninInformation;
import com.sulog.api.model.user.request.LoginRequestDto;
import com.sulog.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public String signIn(LoginRequestDto loginRequestDto){
        Users user = userRepository.findByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword())
                .orElseThrow(InvalidSigninInformation::new);

        Session session = user.addSession();
        return session.getAccessToken();
    }
}
