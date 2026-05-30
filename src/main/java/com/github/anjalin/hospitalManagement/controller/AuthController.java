package com.github.anjalin.hospitalManagement.controller;

import com.github.anjalin.hospitalManagement.dto.LoginRequestDto;
import com.github.anjalin.hospitalManagement.dto.LoginResponseDto;
import com.github.anjalin.hospitalManagement.dto.SignUpRequestDto;
import com.github.anjalin.hospitalManagement.dto.SignupResponseDto;
import com.github.anjalin.hospitalManagement.security.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@Valid @RequestBody SignUpRequestDto signupRequestDto) {
        return ResponseEntity.ok(authService.signup(signupRequestDto));
    }
}
