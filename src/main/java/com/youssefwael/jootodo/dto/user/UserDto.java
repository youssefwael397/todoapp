package com.youssefwael.jootodo.dto.user;

import com.youssefwael.jootodo.entity.User;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private User.Role role;
}
