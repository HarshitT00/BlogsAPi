package com.harshit.blogs.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdatePostRequest {
    @NotEmpty
    private String userName;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
}
