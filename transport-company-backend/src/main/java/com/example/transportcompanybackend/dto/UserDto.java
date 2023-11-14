package com.example.transportcompanybackend.dto;

import com.example.transportcompanybackend.entity.User;
import com.example.transportcompanybackend.etc.Constants;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class UserDto {
    private Long id;
    @Pattern(regexp = Constants.EMAIL_PATTERN, message = Constants.NOT_VALID_EMAIL_MESSAGE)
    private String email;
    private String firstName;
    private String lastName;
    private Boolean isDisabled;
    private User.Role role;
}
