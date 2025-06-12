package com.youssefwael.jootodo.controller;

import com.youssefwael.jootodo.dto.AuthResponseDto;
import com.youssefwael.jootodo.dto.LoginDto;
import com.youssefwael.jootodo.dto.RegisterRequestDto;
import com.youssefwael.jootodo.dto.UserResponseDto;
import com.youssefwael.jootodo.entity.User;
import com.youssefwael.jootodo.service.UserService;
import com.youssefwael.jootodo.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto, BindingResult bindingResult) {
        User user = userService.getUserEntityByEmail(loginDto.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
        }

        boolean isAuthenticated = passwordEncoder.matches(loginDto.getPassword(), user.getPassword());
        if (!isAuthenticated) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(user);

        AuthResponseDto authResponse = new AuthResponseDto(token);
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponseDto> getCurrentUserProfile() {
        // get the token from the header
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("authentication : " + authentication);

        if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        // extract the user's email from the token
        String email = authentication.getName();

        // get the user from the database using the email
        UserResponseDto user = userService.getUserByEmail(email);

        if(user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        // send it to the client
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody RegisterRequestDto registerRequestDto) {

        if (userService.existsByUsername(registerRequestDto.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("username", "Username already exists"));
        }
        if (userService.existsByEmail(registerRequestDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("email", "Email already exists"));
        }
        UserResponseDto savedUser = userService.registerUser(registerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    // ... (other endpoints, you may want to map to DTOs as needed)
}