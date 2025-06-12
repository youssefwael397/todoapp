package com.youssefwael.jootodo.service;

import com.youssefwael.jootodo.dto.RegisterRequestDto;
import com.youssefwael.jootodo.dto.UserResponseDto;
import com.youssefwael.jootodo.dto.UserUpdateDto;
import com.youssefwael.jootodo.entity.User;
import com.youssefwael.jootodo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList());
    }

    public UserResponseDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .orElse(null);
    }

    public User getUserEntityByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserResponseDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user != null ? modelMapper.map(user, UserResponseDto.class) : null;
    }

    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user != null ? modelMapper.map(user, UserResponseDto.class) : null;
    }

    public UserResponseDto registerUser(RegisterRequestDto registerRequestDto) {
        User user = modelMapper.map(registerRequestDto, User.class);
        String hashedPassword = passwordEncoder.encode(registerRequestDto.getPassword());
        user.setPassword(hashedPassword);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserResponseDto.class);
    }

    public UserResponseDto updateUser(Long id, UserUpdateDto userUpdateDto) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) return null;
        modelMapper.map(userUpdateDto, existingUser);
        User savedUser = userRepository.save(existingUser);
        return modelMapper.map(savedUser, UserResponseDto.class);
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