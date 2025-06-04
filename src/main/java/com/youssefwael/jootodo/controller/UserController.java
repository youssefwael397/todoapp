package com.youssefwael.jootodo.controller;

import com.youssefwael.jootodo.dto.UserRequestDto;
import com.youssefwael.jootodo.dto.UserResponseDto;
import com.youssefwael.jootodo.dto.UserUpdateDto;
import com.youssefwael.jootodo.entity.User;
import com.youssefwael.jootodo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponseDto> response = users.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
        }
        return ResponseEntity.ok(convertToResponseDto(user));
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserRequestDto userRequestDto,
            BindingResult bindingResult) {

        Map<String, String> errors = validateRequest(bindingResult);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        if (userService.existsByUsername(userRequestDto.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("username", "Username already exists"));
        }

        if (userService.existsByEmail(userRequestDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("email", "Email already exists"));
        }

        User user = convertToEntity(userRequestDto);
        User saved = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToResponseDto(saved));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDto userRequestDto,
            BindingResult bindingResult) {

        Map<String, String> errors = validateRequest(bindingResult);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        User existingUser = userService.getUserById(id);
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
        }

        User userWithSameUsername = userService.getUserByUsername(userRequestDto.getUsername());
        if (userWithSameUsername != null && !userWithSameUsername.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("username", "Username already exists"));
        }

        User userWithSameEmail = userService.getUserByEmail(userRequestDto.getEmail());
        if (userWithSameEmail != null && !userWithSameEmail.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("email", "Email already exists"));
        }

        User updatedUser = userService.saveUser(existingUser);
        return ResponseEntity.ok(convertToResponseDto(updatedUser));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
        }
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // --- Utility Methods ---

    private Map<String, String> validateRequest(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return errors;
    }

    private UserResponseDto convertToResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }

    private User convertToEntity(UserRequestDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // Make sure to encode it in the service
        user.setRole(User.Role.USER); // default role
        return user;
    }
}
