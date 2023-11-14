package com.example.transportcompanybackend.dto;

import com.example.transportcompanybackend.etc.Constants;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CreateUserDto extends UserDto {
    @Pattern(regexp = Constants.PASSWORD_PATTERN, message = Constants.NOT_VALID_PASSWORD_MESSAGE)
    private String password;
}
