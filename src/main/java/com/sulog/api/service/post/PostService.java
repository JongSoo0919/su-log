package com.sulog.api.service.post;

import com.sulog.api.domain.post.Post;
import com.sulog.api.model.post.request.PostRequestDto;
import com.sulog.api.model.post.request.PostSearchRequestDto;
import com.sulog.api.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post write(PostRequestDto postCreate) {
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        return postRepository.save(post);
    }

    public Post getOne(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글 입니다."));

        return post;
    }
    public Post getRss(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글 입니다."));

        return post;
    }

    /**
     * 게시글 전체를 가져온다.
     * @return
     */
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    /**
     * 게시글 페이징 처리 By Pageable
     * @return
     */
//    public List<Post> getPostsByPaging(Pageable pageable) {
////        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "id"));
////        return postRepository.findAll(pageable);
//    }

    /**
     * 게시글 페이징 처리 By QueryDSL
     * @return
     */
    public List<Post> getPostsByPaging(PostSearchRequestDto postSearchRequestDto) {
        return postRepository.getList(postSearchRequestDto);
    }
}
