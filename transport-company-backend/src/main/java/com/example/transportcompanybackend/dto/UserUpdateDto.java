package com.example.transportcompanybackend.dto;

import lombok.Data;


@Data
public class UserUpdateDto {
    private String email;
    private String firstName;
    private String lastName;
}
