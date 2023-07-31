package com.sulog.api.docs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sulog.api.domain.post.Post;
import com.sulog.api.model.post.request.PostRequestDto;
import com.sulog.api.repository.post.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.su-log.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class RestDocPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("설정 테스트")
    public void 설정_테스트() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("index"));
    }

    @Test
    @DisplayName("글 단건 테스트")
    public void 글_단건_테스트() throws Exception {
        Post post = Post.builder()
                .title("12312312319876543")
                .content("삼겹살 먹고 싶다.")
                .build();

        postRepository.save(post);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/{postId}", post.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("index",
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("postId").description("게시글 ID")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("id").description("게시글 ID"),
                                PayloadDocumentation.fieldWithPath("title").description("글 제목"),
                                PayloadDocumentation.fieldWithPath("content").description("내용")
                        )
                    )
                )
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("글 등록 테스트")
    public void 글_등록_테스트() throws Exception {
        //given
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .title("생성 제목")
                .content("생성 내용입니다.")
                .build();


        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequestDto))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("index",
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("title").description("글 제목"),
                                PayloadDocumentation.fieldWithPath("content").description("글 내용")
                        )
                    )
                )
                .andDo(MockMvcResultHandlers.print());
    }
}
