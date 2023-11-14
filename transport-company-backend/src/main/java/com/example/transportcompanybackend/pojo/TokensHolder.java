package com.example.transportcompanybackend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokensHolder {
    private String accessToken;
    private String refreshToken;
}
