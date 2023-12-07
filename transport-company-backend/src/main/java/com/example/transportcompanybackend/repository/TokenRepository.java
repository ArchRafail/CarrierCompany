package com.example.transportcompanybackend.repository;

import com.example.transportcompanybackend.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByRefreshToken(String refreshToken);

    Optional<Token> findByRefreshTokenAndDeviceId(String refreshToken, String deviceId);

    Optional<Token> findByDeviceId(String deviceId);

    List<Token> findAllByUserId(Long userId);

    @Modifying
    @Query("""
        UPDATE Token t
        SET t.refreshToken = NULL
        WHERE t.deviceId <> :deviceId
        """)
    void updateAllExceptMyTokenWithDeviceId(String deviceId);
}
