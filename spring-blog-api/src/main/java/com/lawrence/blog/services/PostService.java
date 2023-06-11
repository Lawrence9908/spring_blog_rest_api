package com.lawrence.blog.services;

import com.lawrence.blog.payload.PostDto;
import com.lawrence.blog.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

   PostDto updatePost(PostDto postDto, long id);

    PostDto getPostById(long id);
    void deletePostById(long id);

    List<PostDto> getPostByCategory(Long category);
}
