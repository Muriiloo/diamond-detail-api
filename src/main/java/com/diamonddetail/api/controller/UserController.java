package com.diamonddetail.api.controller;

import com.diamonddetail.api.entities.UserEntity;
import com.diamonddetail.api.record.UserResponseDTO;
import com.diamonddetail.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody UserEntity user) {
        if(user.getName() == null || user.getName().isBlank()){
            return ResponseEntity.badRequest().body("Nome é obrigatório!");
        }

        if(user.getName().length() < 3){
            return ResponseEntity.badRequest().body("Nome precisa ter mais de 4 letras!");
        }

        if(!user.getName().matches("^[a-zA-ZÀ-ÿ\\\\s]+$")){
            return ResponseEntity.badRequest().body("Nome não pode ter caracteres especiais!");
        }

        return ResponseEntity.ok(userRepository.save(user));
    }

    @GetMapping("/user-list")
    public List<UserResponseDTO> listUsers(){
       List<UserEntity> users = userRepository.findAll();

       return users.stream().map(user -> new UserResponseDTO(
               user.getName(),
               user.getEmail(),
               user.getType()
       )).toList();
    }
}
