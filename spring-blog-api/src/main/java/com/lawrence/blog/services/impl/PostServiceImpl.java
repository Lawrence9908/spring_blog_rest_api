package com.lawrence.blog.services.impl;

import com.lawrence.blog.entity.Category;
import com.lawrence.blog.entity.Post;
import com.lawrence.blog.exceptions.ResourceNotFoundException;
import com.lawrence.blog.payload.PostDto;
import com.lawrence.blog.payload.PostResponse;
import com.lawrence.blog.repository.CategoryRepository;
import com.lawrence.blog.repository.PostRepository;
import com.lawrence.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepo;
    private final CategoryRepository categoryRepo;
    private final ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepo, CategoryRepository categoryRepo, ModelMapper mapper) {
        this.postRepo = postRepo;
        this.categoryRepo = categoryRepo;
        this.mapper = mapper;
    }
    //Method to add a new post
    @Override
    public PostDto createPost(PostDto postDto) {
        Category category = categoryRepo.findById(postDto.getCategoryId())
                .orElseThrow(()-> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
        // Convert PostDto to PostEntity
        Post post  = mapToEntity(postDto);
        post.setCategory(category);
        Post  addedPost  = postRepo.save(post);
        // Convert entity to dto
        PostDto postResponse  = mapToDto(addedPost);
        return postResponse;
    }
    // Method to get all posts
    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        //Creating pageable instance
        Pageable pageable  = PageRequest.of(pageNo, pageSize, sort);
        Page<Post>  posts = postRepo.findAll(pageable);
        // Get content from page object
        List<Post> listOfPosts = posts.getContent();
        List<PostDto> content = listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse  = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }
    //Method to update post
    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        //Checking of the category with the marching id exist or not
        Post post  = postRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));

        //Checking if the category with the marching id exist or not
        Category category = categoryRepo.findById(postDto.getCategoryId())
                .orElseThrow(()->new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        post.setCategory(category);

        Post updatePost = postRepo.save(post);
        return mapToDto(updatePost);
    }
    //Method to get pot by id
    @Override
    public PostDto getPostById(long id) {
        Post post  = postRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);
    }
   //Method to delete post
    @Override
    public void deletePostById(long id) {
        Post post  = postRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
         postRepo.deleteById(id);
    }
    // Method to get post by category
    @Override
    public List<PostDto> getPostByCategory(Long categoryId) {
       //Checking if the post with the given categoryId it does exist or not
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));
        //Retrieving the post of the matching category
        List<Post> posts  = postRepo.findCategoryById(categoryId);
        //Converting a list of PostEntity into a list of postDto
        List<PostDto> listOfPost = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        return listOfPost;
    }

    //Convert DTO to Entity
    private Post mapToEntity(PostDto postDto){
        Post post  = mapper.map(postDto, Post.class);
//        Post post  = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setContent(postDto.getContent());
//        post.setDescription(postDto.getDescription());
        return post;
    }

    //Convert Entity to DTO
    private PostDto mapToDto(Post post){
        PostDto postDto = mapper.map(post, PostDto.class);
 //       PostDto postDto  = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
//        postDto.setCategoryId(post.getCategory().getId());
        return postDto;
    }
}
