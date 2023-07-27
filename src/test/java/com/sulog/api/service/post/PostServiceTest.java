package com.sulog.api.service.post;

import com.sulog.api.domain.post.Post;
import com.sulog.api.exception.PostNotFoundException;
import com.sulog.api.model.post.PostEdit;
import com.sulog.api.model.post.request.PostRequestDto;
import com.sulog.api.model.post.request.PostSearchRequestDto;
import com.sulog.api.model.post.response.PostResponseDto;
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
    public void clean() {
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
    public void 글_단건_조회() {
        //given
        Post requestPost = Post.builder()
                .title("배고파")
                .content("삼겹살 먹고 싶다.")
                .build();
        postRepository.save(requestPost);

        //when
        Post post = postService.getOne(requestPost.getId());

        //then
        assertNotNull(post);
        assertEquals("배고파", post.getTitle());
        assertEquals("삼겹살 먹고 싶다.", post.getContent());
    }

    @Test
    @DisplayName("글 전체 조회")
    public void 글_전체_조회() {
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
    public void 글_1페이지_조회_id_역순_조회() {
        //given
        List<Post> requestPosts = IntStream.range(0, 30)
                .mapToObj(i -> Post.builder()
                        .title("배고파요~ : " + i)
                        .content("삼겹살 먹고 싶다 : " + i)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        PostSearchRequestDto postSearchRequestDto = PostSearchRequestDto.builder()
                .page(1)
                .build();
        //when
        List<Post> posts = postService.getPostsByPaging(postSearchRequestDto)
                .stream()
                .collect(Collectors.toList());

        //then
        Assertions.assertThat(10).isEqualTo(posts.size());
        Assertions.assertThat("배고파요~ : 29").isEqualTo(posts.get(0).getTitle());
        Assertions.assertThat("배고파요~ : 20").isEqualTo(posts.get(9).getTitle());
    }

    @Test
    @DisplayName("글 제목 수정")
    public void 글_제목_수정() {
        //given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("이건 바꾸고 싶은 제목이야.")
//                .content("이건 바꾸고 싶은 내용이야.")
                .build();

        //when
        postService.edit(post.getId(), postEdit);

        //then
        Post afterPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id : " + post.getId()));
        Assertions.assertThat(afterPost.getTitle()).isEqualTo("이건 바꾸고 싶은 제목이야.");
        Assertions.assertThat(afterPost.getContent()).isEqualTo("내용");
    }

    @Test
    @DisplayName("글 내용 수정")
    public void 글_내용_수정() {
        //given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
//                .title("이건 바꾸고 싶은 제목이야.")
                .content("이건 바꾸고 싶은 내용이야.")
                .build();

        //when
        postService.edit(post.getId(), postEdit);

        //then
        Post afterPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id : " + post.getId()));
        Assertions.assertThat(afterPost.getTitle()).isEqualTo("제목");
        Assertions.assertThat(afterPost.getContent()).isEqualTo("이건 바꾸고 싶은 내용이야.");
    }

    @Test
    @DisplayName("게시글 삭제")
    public void 게시글_삭제(){
        //given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        postRepository.save(post);

        //when
        postService.deleteById(post.getId());

        //then
        Assertions.assertThat(0).isEqualTo(postRepository.count());
    }

    @Test
    @DisplayName("게시글 조회 실패")
    public void 게시글_조회_실패(){
        //given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        postRepository.save(post);

        //when & then
        PostNotFoundException e = org.junit.jupiter.api.Assertions.assertThrows(
                PostNotFoundException.class, () -> {
                    postService.getOne(post.getId() + 1L);
                }
        );

        Assertions.assertThat("존재하지 않는 글 입니다.").isEqualTo(e.getMessage());

    }

    @Test
    @DisplayName("게시글 삭제 실패")
    public void 게시글_삭제_실패(){
        //given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        postRepository.save(post);

        //when & then
        PostNotFoundException e = org.junit.jupiter.api.Assertions.assertThrows(
                PostNotFoundException.class, () -> {
                    postService.deleteById(post.getId() + 1L);
                }
        );

        Assertions.assertThat("존재하지 않는 글 입니다.").isEqualTo(e.getMessage());
    }

    @Test
    @DisplayName("게시글 수정 실패")
    public void 게시글_수정_실패(){
        //given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        postRepository.save(post);

        //when & then
        PostNotFoundException e = org.junit.jupiter.api.Assertions.assertThrows(
                PostNotFoundException.class, () -> {
                    postService.edit(post.getId() + 1L, PostEdit.builder()
                                    .title("수정 실패")
                                    .content("수정 실패")
                            .build());
                }
        );

        Assertions.assertThat("존재하지 않는 글 입니다.").isEqualTo(e.getMessage());
    }
}
