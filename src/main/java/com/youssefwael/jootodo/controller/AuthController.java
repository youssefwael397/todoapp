package com.youssefwael.jootodo.controller;

import com.youssefwael.jootodo.dto.auth.LoginReqDto;
import com.youssefwael.jootodo.dto.auth.LoginResDto;
import com.youssefwael.jootodo.dto.auth.RegisterReqDto;
import com.youssefwael.jootodo.dto.auth.RegisterResDto;
import com.youssefwael.jootodo.dto.user.UserDto;
import com.youssefwael.jootodo.service.impl.AuthServiceImpl;
import com.youssefwael.jootodo.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceImpl userServiceImpl;
    private final AuthServiceImpl authServiceImpl;

    @PostMapping("/login")
    public ResponseEntity<LoginResDto> login(@Valid @RequestBody LoginReqDto loginDto) {
        LoginResDto authResponse = authServiceImpl.authenticate(loginDto);
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getCurrentUserProfile(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = authentication.getName();
        UserDto user = userServiceImpl.getUserByEmail(email);

        return user != null
            ? ResponseEntity.ok(user)
            : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResDto> createUser(
            @Valid @RequestBody RegisterReqDto registerRequestDto) {

        RegisterResDto savedUser = authServiceImpl.registerUser(registerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}