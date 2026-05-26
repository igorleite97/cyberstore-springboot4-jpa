package com.api.CyberStore_API.resources;

import com.api.CyberStore_API.dto.UserDTO;
import com.api.CyberStore_API.dto.UserInsertDTO;
import com.api.CyberStore_API.dto.UserUpdateDTO;
import com.api.CyberStore_API.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    private final UserService service;

    public UserResource(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserInsertDTO dto) {
        UserDTO newDto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newDto.id())
                .toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(
            @PathVariable Long id,
            @Valid
            @RequestBody UserUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}