package com.lawrence.blog.controller;

import com.lawrence.blog.payload.CommentDto;
import com.lawrence.blog.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
   //Create a new comment of a post
   //http://localhost:8080/api/posts/2/comments
    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") Long postId,
                                                    @Valid @RequestBody CommentDto commentDto){
        return  new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }
    //Retrieve comments of a post
    //http://localhost:8080/api/posts/2/comments
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") Long postId){
        return commentService.getCommentsByPostId(postId);
    }
    //Retrieve a comment by id
    //http://localhost:8080/api/posts/2/commnts/3
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") Long postId,
                                                     @PathVariable(value = "id") Long commentId){
            CommentDto commentDto  = commentService.getCommentById(postId, commentId);
            return  new ResponseEntity<>(commentDto, HttpStatus.OK);
    }
    //Update comment endpoint
    //http://localhost:8080/api/posts/1/comments/2
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") Long postId,
                                                    @PathVariable(value = "id") Long commentId,
                                                    @Valid @RequestBody CommentDto commentDto){
        CommentDto comment = commentService.updateComment(postId,commentId, commentDto);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    //Delete comment endpoint
    //http://localhost:8080/api/post/1/comments/1
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId,
                                                @PathVariable(value = "id") Long commentId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment delete successfully", HttpStatus.OK);
    }
}
