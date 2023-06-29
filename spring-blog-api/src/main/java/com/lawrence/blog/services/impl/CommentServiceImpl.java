package com.lawrence.blog.services.impl;

import com.lawrence.blog.entity.Comment;
import com.lawrence.blog.entity.Post;
import com.lawrence.blog.exceptions.BlogAPIException;
import com.lawrence.blog.exceptions.ResourceNotFoundException;
import com.lawrence.blog.payload.CommentDto;
import com.lawrence.blog.payload.PostDto;
import com.lawrence.blog.repository.CommentRepository;
import com.lawrence.blog.repository.PostRepository;
import com.lawrence.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper  = mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));

        comment.setPost(post);
        Comment newComment  = commentRepository.save(comment);
        return mapToDto(comment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        List<Comment> comments  = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        //Retrieve post entity by post id
        Post post  = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
       //Retrieve comment entity by comment id
        Comment comment  = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "id", commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw  new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
        //Retrieve post entity by post id
        Post post  = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
        //Retrieve comment entity by comment id
        Comment comment  = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().equals(post.getId())){
            throw  new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");
        }
        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getEmail());
        comment.setEmail(commentDto.getEmail());

        Comment updateComment  = commentRepository.save(comment);
        return mapToDto(comment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        //Retrieve post entity by post id
        Post post  = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
        //Retrieve comment entity by comment id
        Comment comment  = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", commentId));

        if(!comment.getPost().equals(post.getId())){
            throw  new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");
        }
        commentRepository.delete(comment);

    }

    //Convert comment Entity to Dto
    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto  = mapper.map(comment, CommentDto.class);
//        CommentDto commentDto  = new CommentDto();
//        commentDto.setBody(comment.getBody());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setName(comment.getName());
//        commentDto.setId(comment.getId());
        return commentDto;
    }
    //Convert comment Dto to Entity
    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = mapper.map(commentDto, Comment.class);
//        Comment comment  = new Comment();
//        comment.setBody(commentDto.getBody());
//        comment.setEmail(commentDto.getEmail());
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
        return comment;
    }
}
