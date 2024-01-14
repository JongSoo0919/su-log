package com.sulog.api.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sulog.api.domain.user.Users;
import com.sulog.api.model.post.request.PostRequestDto;
import com.sulog.api.model.user.request.LoginRequestDto;
import com.sulog.api.repository.session.SessionRepository;
import com.sulog.api.repository.user.UserRepository;
import com.sulog.api.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @BeforeEach
    public void clean(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("/login 성공")
    void 로그인_호출() throws Exception {
        //given
        userRepository.save(Users.builder()
                .email("pr@gmail.com")
                .password("1234")
                .build());

        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email("pr@gmail.com")
                .password("1234")
                .build();

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDto))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Transactional
    @DisplayName("/login 성공 후 login 한 개 생성")
    void 로그인_성공후_세션1개생성() throws Exception {
        //given
        Users user = userRepository.save(Users.builder()
                .email("pr@gmail.com")
                .password("1234")
                .build());

        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email("pr@gmail.com")
                .password("1234")
                .build();

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDto))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Users loggedInUser = userRepository.findById(user.getId())
                .orElseThrow(RuntimeException::new);

        Assertions.assertEquals(1L, user.getSessions().size());
    }
}