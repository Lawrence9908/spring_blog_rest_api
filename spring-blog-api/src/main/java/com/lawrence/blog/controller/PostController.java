package com.lawrence.blog.controller;

import com.lawrence.blog.entity.Post;
import com.lawrence.blog.payload.PostDto;
import com.lawrence.blog.payload.PostResponse;
import com.lawrence.blog.services.PostService;
import com.lawrence.blog.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    //Create a new category
    //http://localhost:8080/api/post
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    //Get all posts
    //http://localhost:8080/api/posts
    @GetMapping
    public ResponseEntity<PostResponse> getAllCategories(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        PostResponse list =postService.getAllPosts(pageNo, pageSize, sortBy,sortDir);
       return new ResponseEntity<>(list, HttpStatus.OK);
    }
    //Get post by id
    //http://localhost:8080/api/posts/1
    @GetMapping("{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id){
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }
    //Update post of the given id
    //http://localhost:8080/api/posts/1
    @PutMapping("{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable long id){
        PostDto post  = postService.updatePost(postDto, id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
    //Get all the post of the given id
    //http://localhost:8080/api/posts/category/1
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Long id){
        List<PostDto> postDtos = postService.getPostByCategory(id);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }
    //Delete the post of the given id
    //http://localhost:8080/api/posts/
    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("The post have been deleted successfully", HttpStatus.OK);
    }

}
