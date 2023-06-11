package com.lawrence.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    private long id;
    //Name should not be null or empty
    @NotEmpty(message = "Name should not be null or empty")
    private String name;

    //Email should not be null or empty
    //Email field validation
    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;
    // Comment body should not be null or empty
    //Comment body mus6 be  minimum of 10 characters

    @NotEmpty
    @Size(min = 10, message = "Comment body must be minimum 10 characters")
    private String body;
}
