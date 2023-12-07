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
    private String deviceId;

    public LoginResponse(String accessToken, String refreshToken, AuthUserDto user, String deviceId) {
        super(accessToken, refreshToken);
        this.user = user;
        this.deviceId = deviceId;
    }
}
