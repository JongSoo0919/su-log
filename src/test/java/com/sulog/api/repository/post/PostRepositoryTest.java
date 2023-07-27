package com.sulog.api.repository.post;

import com.sulog.api.domain.post.Post;
import com.sulog.api.model.post.PostEdit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void init(){
        postRepository.deleteAll();
    }

    @DisplayName("게시글을 하나 생성한 후 조회한다.")
    @Test
    public void 게시글생성_후_조회(){
        //given
        Post beforePost = new Post("게시글 입니다.", "콘텐츠 입니다.");

        //when
        postRepository.save(beforePost);
        Post afterPost = postRepository.findAll()
                .stream()
                .findFirst().orElse(null);

        //then
        Assertions.assertThat(beforePost.getTitle()).isEqualTo(afterPost.getTitle());
        Assertions.assertThat(beforePost.getContent()).isEqualTo(afterPost.getContent());
    }

    @DisplayName("게시글을 생성 후 삭제한다.")
    @Test
    public void 게시글생성_후_삭제(){
        //given
        Post beforePost = new Post("게시글 입니다.", "콘텐츠 입니다.");

        //when
        postRepository.save(beforePost);
        postRepository.deleteById(1L);

        //then
        Assertions.assertThat(0).isEqualTo(postRepository.count());
    }

    @DisplayName("게시글을 생성 후 수정한다")
    @Test
    public void 게시글생성_후_수정(){
        //given
        Post beforePost = new Post("게시글 입니다.", "콘텐츠 입니다.");

        //when
        Post afterPost = postRepository.save(beforePost);

        PostEdit postEdit = PostEdit.builder()
                .content("콘텐츠는 수정되었습니다.")
                .build();
        afterPost.update(postEdit);

        //then
        Assertions.assertThat(beforePost.getTitle()).isEqualTo(afterPost.getTitle());
        Assertions.assertThat(beforePost.getContent()).isEqualTo("콘텐츠는 수정되었습니다.");
    }
}