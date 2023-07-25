package com.sulog.api.service.post;

import com.sulog.api.domain.post.Post;
import com.sulog.api.model.post.request.PostRequestDto;
import com.sulog.api.repository.post.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void clean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void 글_작성() {
        //given
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        //when
        postService.write(postRequestDto);

        //then
        Assertions.assertThat(1).isEqualTo(postRepository.count());
    }

    @Test
    @DisplayName("글 1개 조회")
    public void 글_단건_조회(){
        //given
        Post requestPost = Post.builder()
                .title("배고파")
                .content("삼겹살 먹고 싶다.")
                .build();
        postRepository.save(requestPost);

        Long postId = 1L;

        //when
        Post post = postService.getOne(postId);

        //then
        assertNotNull(post);
        assertEquals("배고파", post.getTitle());
        assertEquals("삼겹살 먹고 싶다.",post.getContent());
    }

    @Test
    @DisplayName("글 여러개 조회")
    public void 글_여러개_조회(){
        //given
        Post requestPost1 = Post.builder()
                .title("배고파")
                .content("삼겹살 먹고 싶다.")
                .build();
        postRepository.save(requestPost1);
        Post requestPost2 = Post.builder()
                .title("배고파2")
                .content("삼겹살 먹고 싶다.2")
                .build();
        postRepository.save(requestPost2);


        //when
        List<Post> posts = postService.getAll();

        //then
        Assertions.assertThat(2).isEqualTo(posts.size());
    }

}