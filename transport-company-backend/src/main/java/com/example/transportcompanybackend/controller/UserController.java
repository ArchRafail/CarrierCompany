package com.example.transportcompanybackend.controller;

import com.example.transportcompanybackend.dto.BooleanWrapperDto;
import com.example.transportcompanybackend.dto.PasswordUpdateDto;
import com.example.transportcompanybackend.dto.UserDto;
import com.example.transportcompanybackend.dto.UserUpdateDto;
import com.example.transportcompanybackend.entity.User;
import com.example.transportcompanybackend.security.UserPrincipal;
import com.example.transportcompanybackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public Page<UserDto> getAll(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) User.Role role,
            @RequestParam(required = false) Boolean isDisabled,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) @ParameterObject Pageable pageable) {
        return userService.getAll(id, email, firstName, lastName, role, isDisabled, pageable);
    }

    @PutMapping("/{id}/password")
    public UserDto changePassword(@PathVariable Long id,
                                  @RequestBody PasswordUpdateDto passwordUpdateDto,
                                  @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return userService.changePassword(id, passwordUpdateDto, userPrincipal);
    }

    @PutMapping("/{id}")
    public UserDto updatePersonalInfo(@PathVariable Long id,
                          @RequestBody UserUpdateDto userUpdateDto,
                          @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return userService.updatePersonalInfo(id, userUpdateDto, userPrincipal);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/active")
    public UserDto updateActive(@PathVariable Long id,
                                @RequestBody BooleanWrapperDto booleanWrapper) {
        return userService.updateActive(id, booleanWrapper.getValue());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/role")
    public UserDto changeRole(@PathVariable Long id,
                              @RequestParam User.Role role) {
        return userService.changeRole(id, role);
    }

}
