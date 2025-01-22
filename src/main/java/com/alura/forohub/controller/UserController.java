package com.alura.forohub.controller;

import com.alura.forohub.dto.UserRegistrationDto;
import com.alura.forohub.dto.UserResponseDto;
import com.alura.forohub.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRegistrationDto registrationDto) {
        UserResponseDto registeredUser = userService.registerUser(registrationDto);
        return ResponseEntity.ok(registeredUser);
    }
}
