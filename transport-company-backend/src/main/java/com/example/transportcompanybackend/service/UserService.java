package com.example.transportcompanybackend.service;

import com.example.transportcompanybackend.dto.AuthUserDto;
import com.example.transportcompanybackend.dto.CreateUserDto;
import com.example.transportcompanybackend.dto.UserDto;
import com.example.transportcompanybackend.entity.User;
import com.example.transportcompanybackend.exception.ItemNotFoundException;
import com.example.transportcompanybackend.mapper.Mapper;
import com.example.transportcompanybackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;
    private final PasswordEncoder encoder;

    public UserDto get(Long id) {
        return mapper.toUserDto(retrieve(id));
    }

    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(mapper::toUserDto).toList();
    }

    public void update(Long id, UserDto userDto) {
        User user = retrieve(id);
        mapper.mergeUser(userDto, user);
        userRepository.save(user);
    }

    public void create(CreateUserDto userDto) {
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        userRepository.save(mapper.toUser(userDto));
    }

    public void setPassword(Long id, String password){
        User user = retrieve(id);
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public AuthUserDto getAuthUser(String email) {
        return userRepository.findByEmail(email).map(mapper::toAuthUserDto)
                .orElseThrow(() -> new ItemNotFoundException(
                        String.format("%s: {User, email=%s}", ItemNotFoundException.DEFAULT_MESSAGE, email)
                ));
    }

    public UserDto setDisabled(Long id, Boolean value) {
        User user = retrieve(id);
        user.setIsDisabled(value);
        return mapper.toUserDto(userRepository.save(user));
    }

    private User retrieve(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(User.class, id));
    }
}
