package com.youssefwael.jootodo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TodoResponseDto {
    private Long id;
    private String title;
    private String description;
    private UserResponseDto user;
    private Boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime dueDate;
}