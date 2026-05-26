package com.api.CyberStore_API.dto;

import com.api.CyberStore_API.entities.User;

public record UserDTO(

        Long id,
        String name,
        String email,
        String phone
) {
    public UserDTO(User entity) {
        this(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPhone()
        );
    }

}
