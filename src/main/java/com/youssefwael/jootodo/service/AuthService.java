package com.youssefwael.jootodo.service;

import com.youssefwael.jootodo.dto.auth.LoginReqDto;
import com.youssefwael.jootodo.dto.auth.LoginResDto;
import com.youssefwael.jootodo.dto.auth.RegisterReqDto;
import com.youssefwael.jootodo.dto.auth.RegisterResDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    LoginResDto authenticate(LoginReqDto loginDto);
    RegisterResDto registerUser(RegisterReqDto registerRequestDto);
}
