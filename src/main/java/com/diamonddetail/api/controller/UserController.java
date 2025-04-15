package com.diamonddetail.api.controller;

import com.diamonddetail.api.entities.UserEntity;
import com.diamonddetail.api.record.users.UserCreateDTO;
import com.diamonddetail.api.record.users.UserResponseDTO;
import com.diamonddetail.api.record.users.UserUpdateDTO;
import com.diamonddetail.api.repository.UserRepository;
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
    private UserRepository userRepository;


    @PostMapping("/create-user")
    @Operation(summary = "Cria um novo usuário", description = "Rota para criar um novo usuário no sistema")
    public ResponseEntity<?> createUser(@RequestBody UserCreateDTO user) {
        try{
            if(user.name() == null || user.name().isBlank()){
                return ResponseEntity.badRequest().body("Nome é obrigatório!");
            }

            if(user.name().length() < 3){
                return ResponseEntity.badRequest().body("Nome precisa ter mais de 3 letras!");
            }

            if(!user.name().matches("^[a-zA-ZÀ-ÿ\\\\s]+$")){
                return ResponseEntity.badRequest().body("Nome não pode ter caracteres especiais!");
            }

            userRepository.save(user.toEntity());

            return ResponseEntity.ok("Usuário criado com sucesso!");
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar usuário!");
        }
    }

    @GetMapping("/user-list")
    @Operation(summary = "Lista os usuários cadastrados", description = "Rota para listar os usuários cadastrados do sistema")
    public ResponseEntity<?> listUsers(){
       try{
           List<UserEntity> users = userRepository.findAll();

           if(users.isEmpty()){
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum usuário encontrado!");
           }

           List<UserResponseDTO> response = users.stream().map(user -> new UserResponseDTO(
                   user.getName(),
                   user.getEmail(),
                   user.getType()
           )).toList();

           return ResponseEntity.ok(response);
       }catch (Exception ex){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar usuário!");
       }
    }

    @PutMapping("/update-user/{id}")
    @Operation(summary = "Altera um usuário pelo id", description = "Rota para alterar um usuário cadastrado no sistema")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody UserUpdateDTO userUpdateDTO){
        try {
            Optional<UserEntity> optionalUser = userRepository.findById(id);

            if(optionalUser.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
            }

            UserEntity user = optionalUser.get();

            if(userUpdateDTO.name() != null && !userUpdateDTO.name().isBlank()){

                if(!user.getName().matches("^[a-zA-ZÀ-ÿ\\\\s]+$")){
                    return ResponseEntity.badRequest().body("Nome não pode ter caracteres especiais!");
                }

                user.setName(userUpdateDTO.name());
            }

            if(userUpdateDTO.email() != null && !userUpdateDTO.email().isBlank()){
                user.setEmail(userUpdateDTO.email());
            }

            if(userUpdateDTO.password() != null && !userUpdateDTO.password().isBlank()){
                user.setPassword(userUpdateDTO.password());
            }

            if(userUpdateDTO.type() != null){
                user.setType(userUpdateDTO.type());
            }

            userRepository.save(user);

            return ResponseEntity.ok("Usuário atualizado!");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o usuário.");
        }
    }

    @PatchMapping("/update-patch-user/{id}")
    @Operation(summary = "Altera um usuário pelo id", description = "Rota para alterar um usuário cadastrado no sistema")
    public ResponseEntity<?> updatePatchUser(@PathVariable Integer id, @RequestBody UserUpdateDTO userUpdateDTO) {
        try{
            Optional<UserEntity> optionalUser = userRepository.findById(id);

            if(optionalUser.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
            }

            UserEntity user = optionalUser.get();

            if(userUpdateDTO.name() != null && !userUpdateDTO.name().isBlank()){

                if (!userUpdateDTO.name().matches("^[a-zA-ZÀ-ÿ\\s]+$")) {
                    return ResponseEntity.badRequest().body("Nome não pode ter caracteres especiais!");
                }

                user.setName(userUpdateDTO.name());
            }

            if(userUpdateDTO.email() != null && !userUpdateDTO.email().isBlank()){
                user.setEmail(userUpdateDTO.email());
            }

            if(userUpdateDTO.password() != null && !userUpdateDTO.password().isBlank()){
                user.setPassword(userUpdateDTO.password());
            }

            if(userUpdateDTO.type() != null){
                user.setType(userUpdateDTO.type());
            }

            userRepository.save(user);

            return ResponseEntity.ok("Usuário atualizado!");
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o usuário!");
        }
    }

    @DeleteMapping("/delete-user/{id}")
    @Operation(summary = "Deletar um usuário pelo id", description = "Rota para deletar um usuário cadastrado no sistema")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {

        try{
            Optional<UserEntity> optionalUser = userRepository.findById(id);

            if(optionalUser.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
            }

            UserEntity user = optionalUser.get();
            if(!user.getId().equals(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID do usuário não confere!");
            }

            userRepository.deleteById(id);
            return ResponseEntity.ok("Usuário deletado com sucesso!");

        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar o usuário!");
        }
    }
}
