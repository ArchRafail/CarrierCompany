package com.example.transportcompanybackend.dto;

import com.example.transportcompanybackend.entity.User;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AuthUserDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private User.Role role;
}
