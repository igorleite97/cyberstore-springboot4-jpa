package com.api.CyberStore_API.services;

import java.util.List;
import java.util.stream.Collectors;

import com.api.CyberStore_API.dto.UserDTO;
import com.api.CyberStore_API.dto.UserInsertDTO;
import com.api.CyberStore_API.dto.UserUpdateDTO;
import com.api.CyberStore_API.services.exceptions.DatabaseException;
import com.api.CyberStore_API.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.api.CyberStore_API.entities.User;
import com.api.CyberStore_API.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<UserDTO> findAll() {
        List<User> list = repository.findAll();
        return list.stream()
        .map(UserDTO::new)
        .collect(Collectors.toList());
    }

    public UserDTO findById(Long id) {
        User entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id));
            return new UserDTO(entity);
    }

    public UserDTO insert(UserInsertDTO dto) {
        User entity = new User();
        copyDtoToEntity(dto, entity);
        entity.setPassword(dto.getPassword());
        entity = repository.save(entity);
        return new UserDTO(entity);
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

    public UserDTO update(Long id, UserUpdateDTO dto) {
        try {
            User entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new UserDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void copyDtoToEntity(UserDTO dto, User entity) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
    }
}