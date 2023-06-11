package com.lawrence.blog.repository;

import com.lawrence.blog.entity.Post;
import com.lawrence.blog.payload.PostDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findCategoryById(Long id);
}
