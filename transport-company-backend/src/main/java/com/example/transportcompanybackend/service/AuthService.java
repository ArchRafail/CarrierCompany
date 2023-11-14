package com.example.transportcompanybackend.service;

import com.example.transportcompanybackend.dto.AuthUserDto;
import com.example.transportcompanybackend.dto.CredentialsDto;
import com.example.transportcompanybackend.dto.LoginResponse;
import com.example.transportcompanybackend.pojo.TokensHolder;
import com.example.transportcompanybackend.security.DatabaseUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;


@RequiredArgsConstructor
@Service
public class AuthService {
    private final DatabaseUserService databaseUserService;
    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    public LoginResponse login(CredentialsDto credentialsDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentialsDto.getEmail(), credentialsDto.getPassword()));
        UserDetails userDetails = databaseUserService.loadUserByUsername(credentialsDto.getEmail());

        String accessToken = jwtTokenService.generateAccessToken(userDetails);
        String refreshToken = jwtTokenService.generateRefreshToken(accessToken);
        AuthUserDto authUserDto = userService.getAuthUser(userDetails.getUsername());
        return new LoginResponse(accessToken, refreshToken, authUserDto);
    }

    public TokensHolder refreshToken(String refreshToken) {
        UserDetails userDetails = databaseUserService.loadUserByUsername(jwtTokenService.getEmail(refreshToken));

        Date refreshTokenExpiration = jwtTokenService.getRefreshExpirationDate(refreshToken);
        String accessToken = jwtTokenService.generateAccessToken(userDetails);
        String newRefreshToken;
        if (refreshTokenExpiration.after(new Date())) {
            newRefreshToken = jwtTokenService.generateRefreshToken(accessToken, refreshTokenExpiration);
        } else {
            throw new AuthenticationServiceException("Session expired");
        }
        return new TokensHolder(accessToken, newRefreshToken);
    }
}
