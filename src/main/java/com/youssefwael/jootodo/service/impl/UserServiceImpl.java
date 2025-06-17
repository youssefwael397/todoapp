package com.youssefwael.jootodo.service.impl;

import com.youssefwael.jootodo.dto.auth.RegisterReqDto;
import com.youssefwael.jootodo.dto.auth.RegisterResDto;
import com.youssefwael.jootodo.dto.user.UserDto;
import com.youssefwael.jootodo.dto.user.UserUpdateDto;
import com.youssefwael.jootodo.entity.User;
import com.youssefwael.jootodo.repository.UserRepository;
import com.youssefwael.jootodo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElse(null);
    }

    public User getUserEntityByEmail(String email) {
//        return userRepository.findUserByEmail(email);
        return userRepository.findByEmail(email);
    }

    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user != null ? modelMapper.map(user, UserDto.class) : null;
    }

    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user != null ? modelMapper.map(user, UserDto.class) : null;
    }

    public RegisterResDto registerUser(RegisterReqDto registerRequestDto) {
        User user = modelMapper.map(registerRequestDto, User.class);
        String hashedPassword = passwordEncoder.encode(registerRequestDto.getPassword());
        user.setPassword(hashedPassword);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, RegisterResDto.class);
    }

    public UserDto updateUser(Long id, UserUpdateDto userUpdateDto) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) return null;
        modelMapper.map(userUpdateDto, existingUser);
        User savedUser = userRepository.save(existingUser);
        return modelMapper.map(savedUser, UserDto.class);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}