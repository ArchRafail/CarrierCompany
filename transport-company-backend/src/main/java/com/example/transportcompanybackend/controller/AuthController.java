package com.example.transportcompanybackend.controller;

import com.example.transportcompanybackend.dto.CredentialsDto;
import com.example.transportcompanybackend.dto.LoginResponse;
import com.example.transportcompanybackend.dto.TokenResponse;
import com.example.transportcompanybackend.mapper.Mapper;
import com.example.transportcompanybackend.pojo.TokensHolder;
import com.example.transportcompanybackend.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final Mapper mapper;
    private final String REFRESH_TOKEN_KEY = "REFRESH_TOKEN";

    @Value("${jwt.refresh.cookies.http-only}")
    private boolean cookieHttpOnly;
    @Value("${jwt.refresh.cookies.secured}")
    private boolean cookieSecured;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody CredentialsDto credentialsDto, HttpServletResponse response) {
        LoginResponse loginResponse = authService.login(credentialsDto);
        addRefreshTokenCookie(response, loginResponse.getRefreshToken());
        return loginResponse;
    }

    @PostMapping("/refreshtoken")
    public TokenResponse refreshToken(@CookieValue(REFRESH_TOKEN_KEY) String refreshToken, @RequestBody String deviceId, HttpServletResponse response) {
        TokensHolder tokensHolder = authService.refreshToken(refreshToken, deviceId);
        addRefreshTokenCookie(response, tokensHolder.getRefreshToken());
        return mapper.toTokenResponse(tokensHolder);
    }

    private void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie(REFRESH_TOKEN_KEY, refreshToken);
        cookie.setSecure(cookieSecured);
        cookie.setHttpOnly(cookieHttpOnly);
        response.addCookie(cookie);
    }
}
