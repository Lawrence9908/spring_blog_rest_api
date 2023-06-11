package com.lawrence.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    private long id;
    /*
     * Title should not be null or empty
     * Title should have at least 2 characters
    */
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;
    /*
    * Post description should be not null ore empty
    * Post description should have at least 10 characters
    */
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;

    // Post content should not be bull or empty;
    @NotEmpty
    private String content;

    private Set<CommentDto> comments;

    private Long categoryId;
}
