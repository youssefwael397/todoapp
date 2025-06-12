package com.youssefwael.jootodo.controller;

import com.youssefwael.jootodo.dto.UserResponseDto;
import com.youssefwael.jootodo.dto.UserUpdateDto;
import com.youssefwael.jootodo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getCurrentUserProfile(Authentication authentication) {
        String email = authentication.getName();
        UserResponseDto user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        UserResponseDto user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateCurrentUser(
            @Valid @RequestBody UserUpdateDto userUpdateDto,
            BindingResult bindingResult,
            Authentication authentication) {

        Map<String, String> errors = validateRequest(bindingResult);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        String email = authentication.getName();
        UserResponseDto currentUser = userService.getUserByEmail(email);
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
        }

        UserResponseDto updated = userService.updateUser(currentUser.getId(), userUpdateDto);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDto userUpdateDto,
            BindingResult bindingResult) {

        Map<String, String> errors = validateRequest(bindingResult);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        UserResponseDto updated = userService.updateUser(id, userUpdateDto);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
        }
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        UserResponseDto user = userService.getUserById(id);
        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}