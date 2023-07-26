package com.sulog.api.repository.post;

import com.sulog.api.domain.post.Post;
import com.sulog.api.model.post.request.PostSearchRequestDto;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> getList(PostSearchRequestDto postSearchRequestDto);
}
