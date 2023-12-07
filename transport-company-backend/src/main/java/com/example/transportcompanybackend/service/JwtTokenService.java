package com.example.transportcompanybackend.service;

import com.example.transportcompanybackend.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.*;

import static java.util.Optional.ofNullable;


@Component
public class JwtTokenService {
    @Value("${jwt.access.secret}")
    private String accessSecret;
    @Value("${jwt.refresh.secret}")
    private String refreshSecret;
    @Value("${jwt.access.validity-time}")
    private Duration jwtAccessLifeTime;
    @Value("${jwt.refresh.validity-time}")
    private Duration jwtRefreshLifeTime;

    private final TokenService tokenService;

    public JwtTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public String generateAccessToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElseThrow(() -> new AuthenticationServiceException(
                        "Couldn't find granted authority (role) for user " + userDetails.getUsername())
                );
        claims.put("role", role);
        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + jwtAccessLifeTime.toMillis());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(Keys.hmacShaKeyFor(accessSecret.getBytes()))
                .compact();
    }

    public String generateRefreshToken(String accessToken, String deviceId){
        return generateRefreshToken(accessToken, null, deviceId);
    }

    public String generateRefreshToken(String accessToken, Date expiredDate, String deviceId){
        Date issuedDate = new Date();
        Date expirationDate = ofNullable(expiredDate).orElse(new Date(issuedDate.getTime() + jwtRefreshLifeTime.toMillis()));
        String token = Jwts.builder()
                .setSubject(getUsername(accessToken))
                .setIssuedAt(issuedDate)
                .setExpiration(expirationDate)
                .setId(UUID.randomUUID().toString())
                .signWith(Keys.hmacShaKeyFor(refreshSecret.getBytes()))
                .compact();
        tokenService.saveToken(deviceId, getUsername(accessToken), token, new Timestamp(expirationDate.getTime()));
        return token;
    }

    public String getUsername(String token) {
        return getAllClaimsFromAccessToken(token).getSubject();
    }

    public String getEmail(String token) {
        return getAllClaimsFromRefreshToken(token).getSubject();
    }

    public User.Role getRole(String token) {
        return User.Role.valueOf(getAllClaimsFromAccessToken(token).get("role", String.class));
    }

    public Date getRefreshExpirationDate(String token) { return getAllClaimsFromRefreshToken(token).getExpiration(); }

    private Claims getAllClaimsFromAccessToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(accessSecret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Claims getAllClaimsFromRefreshToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(refreshSecret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
