package com.youssefwael.jootodo.dto;

import com.youssefwael.jootodo.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private User.Role role;
}
