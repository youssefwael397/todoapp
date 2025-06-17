package com.youssefwael.jootodo.dto;

import com.youssefwael.jootodo.dto.user.UserDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TodoResponseDto {
    private Long id;
    private String title;
    private String description;
    private UserDto user;
    private Boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime dueDate;
}