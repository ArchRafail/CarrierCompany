package com.example.transportcompanybackend.dto;

import lombok.Data;


@Data
public class PasswordUpdateDto {
    private String oldPassword;
    private String newPassword;
}
