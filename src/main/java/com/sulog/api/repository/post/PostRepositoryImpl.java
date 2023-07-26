package com.sulog.api.repository.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sulog.api.domain.post.Post;
import com.sulog.api.domain.post.QPost;
import com.sulog.api.model.post.request.PostSearchRequestDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Post> getList(PostSearchRequestDto postSearchRequestDto) {
        return jpaQueryFactory.selectFrom(QPost.post)
                .limit(postSearchRequestDto.getSize())
                .offset(postSearchRequestDto.getOffset())
                .orderBy(QPost.post.id.desc())
                .fetch();
    }
}
