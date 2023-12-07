package com.example.transportcompanybackend.service;

import com.example.transportcompanybackend.entity.Token;
import com.example.transportcompanybackend.entity.User;
import com.example.transportcompanybackend.exception.ItemNotFoundException;
import com.example.transportcompanybackend.repository.TokenRepository;
import com.example.transportcompanybackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;


@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    public void saveToken(String deviceId, String username, String token, Timestamp expirationDate) {
        Token refreshToken = tokenRepository.findByDeviceId(deviceId).orElse(null);
        if (refreshToken == null) {
            refreshToken = new Token();
            refreshToken.setUser(userRepository.findByEmail(username).orElseThrow(ItemNotFoundException::new));
            refreshToken.setDeviceId(deviceId);
        }
        refreshToken.setRefreshToken(token);
        refreshToken.setExpiredDate(new Timestamp(expirationDate.getTime()));
        tokenRepository.save(refreshToken);
    }

    public void removeRefreshTokenByValue(String refreshToken) {
        Token token = tokenRepository.findByRefreshToken(refreshToken).orElse(null);
        if (token == null) { return; }
        setTokenToNull(token);
    }

    public void removeRefreshTokensByUsername(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(ItemNotFoundException::new);
        List<Token> tokens = tokenRepository.findAllByUserId(user.getId());
        if (tokens.isEmpty()) {
            return;
        }
        removeRefreshTokens(tokens);
    }

    private void removeRefreshTokens(List<Token> tokens) {
        tokens.forEach(this::setTokenToNull);
    }

    public boolean refreshTokenNotCorrespondDeviceId(String refreshToken, String deviceId) {
        Token token = tokenRepository.findByRefreshTokenAndDeviceId(refreshToken, deviceId).orElse(null);
        return token == null;
    }

    private void setTokenToNull(Token token) {
        token.setRefreshToken(null);
        tokenRepository.save(token);
    }

    public void removeAllRefreshTokens(String deviceId) {
        tokenRepository.updateAllExceptMyTokenWithDeviceId(deviceId);
    }
}
