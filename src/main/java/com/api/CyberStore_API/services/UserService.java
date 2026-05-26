package com.api.CyberStore_API.services;

import com.api.CyberStore_API.dto.UserDTO;
import com.api.CyberStore_API.dto.UserInsertDTO;
import com.api.CyberStore_API.dto.UserUpdateDTO;
import com.api.CyberStore_API.entities.User;
import com.api.CyberStore_API.repositories.UserRepository;
import com.api.CyberStore_API.services.exceptions.DatabaseException;
import com.api.CyberStore_API.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    // ┌─────────────────────────────────────────────────────┐
    // │  Constructor Injection (sem @Autowired)             │
    // └─────────────────────────────────────────────────────┘
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository repository,
            PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();  // ← Java 16+ (mais moderno que collect(Collectors.toList()))
    }

    public UserDTO findById(Long id) {
        User entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return toDTO(entity);
    }

    public UserDTO insert(UserInsertDTO dto) {
        User entity = new User();
        copyDtoToEntity(dto, entity);
        entity.setPassword(passwordEncoder.encode(dto.password()));
        //                                         ↑ dto.password() sem "get"
        entity = repository.save(entity);
        return toDTO(entity);
    }

    public UserDTO update(Long id, UserUpdateDTO dto) {
        try {
            User entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return toDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    // ┌─────────────────────────────────────────────────────┐
    // │  Métodos privados de conversão                     │
    // └─────────────────────────────────────────────────────┘
    private UserDTO toDTO(User entity) {
        return new UserDTO(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPhone()
        );
    }

    private void copyDtoToEntity(UserInsertDTO dto, User entity) {
        entity.setName(dto.name());     // ← dto.name() sem "get"
        entity.setEmail(dto.email());
        entity.setPhone(dto.phone());
    }

    private void copyDtoToEntity(UserUpdateDTO dto, User entity) {
        entity.setName(dto.name());
        entity.setEmail(dto.email());
        entity.setPhone(dto.phone());
    }
}