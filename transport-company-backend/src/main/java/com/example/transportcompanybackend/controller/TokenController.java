package com.example.transportcompanybackend.controller;

import com.example.transportcompanybackend.service.TokenService;
import com.sun.security.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/token")
public class TokenController {
    private final TokenService tokenService;

    @PostMapping("/remove-my-tokens")
    public void removeMyTokens(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        tokenService.removeRefreshTokensByUsername(userPrincipal.getName());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/remove-user-tokens")
    public void removeTokensByUsername(@RequestBody String username) {
        tokenService.removeRefreshTokensByUsername(username);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/remove-all-tokens")
    public void removeAllTokens(@RequestBody String deviceId) {
        tokenService.removeAllRefreshTokens(deviceId);
    }
}
