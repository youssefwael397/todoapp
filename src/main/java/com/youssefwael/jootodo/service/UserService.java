package com.youssefwael.jootodo.service;

import com.youssefwael.jootodo.dto.auth.RegisterReqDto;
import com.youssefwael.jootodo.dto.auth.RegisterResDto;
import com.youssefwael.jootodo.dto.user.UserDto;
import com.youssefwael.jootodo.dto.user.UserUpdateDto;
import com.youssefwael.jootodo.entity.User;

import java.util.List;

public interface UserService {
    public List<UserDto> getAllUsers();

    public UserDto getUserById(Long id);

    public User getUserEntityByEmail(String email);

    public UserDto getUserByUsername(String username);

    public UserDto getUserByEmail(String email);

    public RegisterResDto registerUser(RegisterReqDto registerRequestDto);

    public UserDto updateUser(Long id, UserUpdateDto userUpdateDto);

    public void deleteUser(Long id);

    public boolean existsByUsername(String username);

    public boolean existsByEmail(String email);
}
