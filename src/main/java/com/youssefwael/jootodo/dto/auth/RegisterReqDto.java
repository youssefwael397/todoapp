package com.youssefwael.jootodo.dto.auth;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterReqDto {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    private String password;

    @NotBlank(message = "Confirm Password is required")
    private String confirmPassword;

    @AssertTrue(message = "Passwords do not match")
    private boolean isPasswordMatch(){
     return password == null || confirmPassword == null || password.equals(confirmPassword);
    }
}
