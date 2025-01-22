package com.alura.forohub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthenticationRequestDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
}
