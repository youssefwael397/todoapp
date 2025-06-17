package com.youssefwael.jootodo.service.impl;

import com.youssefwael.jootodo.dto.auth.LoginReqDto;
import com.youssefwael.jootodo.dto.auth.LoginResDto;
import com.youssefwael.jootodo.dto.auth.RegisterReqDto;
import com.youssefwael.jootodo.dto.auth.RegisterResDto;
import com.youssefwael.jootodo.dto.user.UserDto;
import com.youssefwael.jootodo.entity.User;
import com.youssefwael.jootodo.service.UserService;
import com.youssefwael.jootodo.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {
    
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public LoginResDto authenticate(LoginReqDto loginDto) {
        User user = userService.getUserEntityByEmail(loginDto.getEmail());
        
        if (user == null || !passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user);
        return new LoginResDto(token);
    }

    public RegisterResDto registerUser(RegisterReqDto registerRequestDto) {
        validateUserDoesNotExist(registerRequestDto);
        return userService.registerUser(registerRequestDto);
    }

    private void validateUserDoesNotExist(RegisterReqDto registerRequestDto) {
        if (userService.existsByUsername(registerRequestDto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        if (userService.existsByEmail(registerRequestDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
    }
}