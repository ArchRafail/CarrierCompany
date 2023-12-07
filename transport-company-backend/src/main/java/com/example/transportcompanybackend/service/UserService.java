package com.example.transportcompanybackend.service;

import com.example.transportcompanybackend.dto.*;
import com.example.transportcompanybackend.entity.User;
import com.example.transportcompanybackend.exception.ItemNotFoundException;
import com.example.transportcompanybackend.mapper.Mapper;
import com.example.transportcompanybackend.repository.UserRepository;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;
    private final PasswordEncoder encoder;

    public Page<UserDto> getAll(Long id, String email, String firstName, String lastName, User.Role role, Boolean isDisabled, Pageable pageable) {
        return userRepository.findAllBy(id, email, firstName, lastName, role, isDisabled, pageable).map(mapper::toUserDto);
    }

    public UserDto get(Long id) {
        return mapper.toUserDto(retrieve(id));
    }

    public void create(CreateUserDto userDto) {
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        userRepository.save(mapper.toUser(userDto));
    }

    public AuthUserDto getAuthUser(String email) {
        return userRepository.findByEmail(email).map(mapper::toAuthUserDto)
                .orElseThrow(() -> new ItemNotFoundException(
                        String.format("%s: {User, email=%s}", ItemNotFoundException.DEFAULT_MESSAGE, email)
                ));
    }

    public UserDto changePassword(Long id, PasswordUpdateDto passwordUpdateDto) {
        User user = retrieve(id);
        if (!encoder.matches(passwordUpdateDto.getOldPassword(), user.getPassword())) {
            throw new ValidationException("Password can't be changed");
        }
        user.setPassword(encoder.encode(passwordUpdateDto.getNewPassword()));
        return mapper.toUserDto(userRepository.save(user));
    }

    public UserDto update(Long id, UserUpdateDto userUpdateDto) {
        User user = retrieve(id);
        user = mapper.patchUser(userUpdateDto, user);
        return mapper.toUserDto(userRepository.save(user));
    }

    public UserDto updateActive(Long id, Boolean value) {
        User user = retrieve(id);
        user.setIsDisabled(!value);
        return mapper.toUserDto(userRepository.save(user));
    }

    public UserDto changeRole(Long id, User.Role role) {
        User user = retrieve(id);
        user.setRole(role);
        return mapper.toUserDto(userRepository.save(user));
    }

    private User retrieve(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(User.class, id));
    }
}
