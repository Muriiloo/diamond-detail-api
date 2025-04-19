package com.diamonddetail.api.controller;

import com.diamonddetail.api.entities.UserEntity;
import com.diamonddetail.api.record.users.UserCreateDTO;
import com.diamonddetail.api.record.users.UserResponseDTO;
import com.diamonddetail.api.record.users.UserUpdateDTO;
import com.diamonddetail.api.repository.UserRepository;
import com.diamonddetail.api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuários", description = "Operações relacionadas a usuário")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/create-user")
    @Operation(summary = "Cria um novo usuário", description = "Rota para criar um novo usuário no sistema")
    public ResponseEntity<?> createUser(@RequestBody UserCreateDTO user) {
        try{
            return ResponseEntity.ok(userService.createUser(user));
        }catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar ao usuário!");
        }
    }

    @GetMapping("/user-list")
    @Operation(summary = "Lista os usuários cadastrados", description = "Rota para listar os usuários cadastrados do sistema")
    public ResponseEntity<?> listUsers(){
       try{
           return ResponseEntity.ok(userService.listUser());
       }catch (IllegalArgumentException ex){
           return ResponseEntity.badRequest().body(ex.getMessage());
       }
       catch (Exception ex){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar usuário!");
       }
    }

    @PatchMapping("/update-user/{id}")
    @Operation(summary = "Altera um usuário pelo id", description = "Rota para alterar um usuário cadastrado no sistema")
    public ResponseEntity<?> updatePatchUser(@PathVariable Integer id, @RequestBody UserUpdateDTO userUpdateDTO) {
        try {
            return ResponseEntity.ok(userService.updateUser(id, userUpdateDTO));
        }catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o usuário.");
        }
    }

    @DeleteMapping("/delete-user/{id}")
    @Operation(summary = "Deletar um usuário pelo id", description = "Rota para deletar um usuário cadastrado no sistema")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {

        try{
            return ResponseEntity.ok(userService.deleteUser(id));
        }catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar o usuário!");
        }
    }
}
