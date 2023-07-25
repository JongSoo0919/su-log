package com.sulog.api.controller.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sulog.api.domain.post.Post;
import com.sulog.api.model.post.request.PostRequestDto;
import com.sulog.api.model.post.response.PostResponseDto;
import com.sulog.api.repository.post.PostRepository;
import org.hamcrest.Matchers;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.regex.Matcher;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void clean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청 시 Hello World 출력")
    void 예시_컨트롤러_동작_확인() throws Exception {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("title","제목입니다.");
        multiValueMap.add("content","내용입니다.");

        PostResponseDto response = PostResponseDto.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();



        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/get-test")
                        .params(multiValueMap))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(response)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("/posts 요청 시 title 값은 필수")
    void 예시_컨트롤러_post_empty_title() throws Exception {
        //given
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .title("")
                .content("내용입니다.")
                .build();

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequestDto))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("잘못된 요청입니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("/posts 요청 시 DB에 값이 저장된다.")
    void writeTest() throws Exception {
        //given
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequestDto))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("1"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("글 단건 조회")
    public void 글_단건_조회() throws Exception{
        //given
        Post post = Post.builder()
                .title("배고파")
                .content("삼겹살 먹고 싶다.")
                .build();

        postRepository.save(post);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(post.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("배고파"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("삼겹살 먹고 싶다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("RSS는 타이틀 중 10글자만 반환 되어야 한다.")
    public void RSS_글_단건_조회() throws Exception{
        //given
        Post post = Post.builder()
                .title("12312312319876543")
                .content("삼겹살 먹고 싶다.")
                .build();

        postRepository.save(post);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}/rss", post.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(post.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("1231231231"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("삼겹살 먹고 싶다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("글 다수 조회")
    public void 글_다수_조회() throws Exception{
        //given
        Post post1 = Post.builder()
                .title("배고파1")
                .content("삼겹살 먹고 싶다.1")
                .build();
        Post post2 = Post.builder()
                .title("배고파2")
                .content("삼겹살 먹고 싶다.2")
                .build();

        postRepository.save(post1);
        postRepository.save(post2);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(post1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("배고파1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("삼겹살 먹고 싶다.1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(post2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("배고파2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content").value("삼겹살 먹고 싶다.2"))
                .andDo(MockMvcResultHandlers.print());
    }

}