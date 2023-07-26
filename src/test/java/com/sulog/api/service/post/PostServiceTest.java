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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    @DisplayName("글 전체 조회")
    public void 글_전체_조회(){
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
    @Test
    @DisplayName("글 1페이지 조회 id 역순 조회")
    public void 글_1페이지_조회_id_역순_조회(){
        //given
        List<Post> requestPosts = IntStream.range(0, 30)
                .mapToObj(i -> Post.builder()
                        .title("배고파요~ : " + i)
                        .content("삼겹살 먹고 싶다 : "+ i)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));

        //when
        List<Post> posts = postService.getPostsByPaging(pageable)
                .stream()
                .collect(Collectors.toList());

        //then
        Assertions.assertThat(5).isEqualTo(posts.size());
        Assertions.assertThat("배고파요~ : 29").isEqualTo(posts.get(0).getTitle());
        Assertions.assertThat("배고파요~ : 25").isEqualTo(posts.get(4).getTitle());
    }
}