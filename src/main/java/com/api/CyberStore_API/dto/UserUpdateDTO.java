package com.api.CyberStore_API.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Entrada de atualização
public record UserUpdateDTO(

        @NotBlank(message = "Name is required")
        @Size(min = 3, max = 80)
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid E-mail")
        String email,

        @NotBlank(message = "Phone is required")
        String phone
        // ↑ SEM password — troca de senha é fluxo separado em produção


) {
}
