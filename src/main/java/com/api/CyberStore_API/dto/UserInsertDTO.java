package com.api.CyberStore_API.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Entrada de criação
public record UserInsertDTO(

        @NotBlank(message = "Name is required")
        @Size(min = 3, max = 80)
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email")
        String email,

        @NotBlank(message = "Phone is required")
        String phone,

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must have at least 6 characters")
        String password


) {}
