package com.harshit.blogs.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotEmpty
    @NotBlank(message = "UserName required")
    private String userName;

    @NotEmpty
    @NotBlank(message = "Email required")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;
}
