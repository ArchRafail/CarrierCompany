package com.example.transportcompanybackend.dto;

import com.example.transportcompanybackend.pojo.TokensHolder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class LoginResponse extends TokensHolder {
    private AuthUserDto user;

    public LoginResponse(String accessToken, String refreshToken, AuthUserDto user) {
        super(accessToken, refreshToken);
        this.user = user;
    }
}
