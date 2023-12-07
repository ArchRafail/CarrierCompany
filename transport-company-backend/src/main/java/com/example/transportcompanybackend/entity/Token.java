package com.example.transportcompanybackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "token")
@Entity
public class Token {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private String refreshToken;
    private Timestamp expiredDate;
    private String deviceId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
