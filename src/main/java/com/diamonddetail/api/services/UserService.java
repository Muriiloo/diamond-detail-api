package com.diamonddetail.api.services;

import com.diamonddetail.api.entities.UserEntity;
import com.diamonddetail.api.enums.UserType;
import com.diamonddetail.api.record.users.UserCreateDTO;
import com.diamonddetail.api.record.users.UserResponseDTO;
import com.diamonddetail.api.record.users.UserUpdateDTO;
import com.diamonddetail.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public String createUser(UserCreateDTO user){

        if(user.name() == null || user.name().isBlank()){
            throw new IllegalArgumentException("Nome é obrigatório!");
        }

        if(user.name().length() < 3){
            throw new IllegalArgumentException("Nome precisa ser mais de 4 letras!");
        }

        if (!user.name().matches("^[a-zA-ZÀ-ÿ\\s]+$")) {
            throw new IllegalArgumentException("Nome não pode conter caracteres especiais!");
        }

        if(user.password().length() < 3){
            throw new IllegalArgumentException("Senha precisa ter 3 caracteres ou mais!");
        }

        if(user.email().length() < 5 || !user.email().contains("@")){
            throw new IllegalArgumentException("Esse email é inválido!");
        }

        if(user.type() == null){
            throw new IllegalArgumentException("O tipo precisa ser CLIENTE ou FUNCIONÁRIO!");
        }

        userRepository.save(user.toEntity());
        return "Usuário criado com sucesso!";
    }

    public List<UserResponseDTO> listUser(){
        List<UserEntity> users = userRepository.findAll();

        return users.stream().map(user -> new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getType()
        )).toList();
    }

    public String updateUser(Integer id, UserUpdateDTO userUpdateDTO){
        UserEntity user = userRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Usuário não encontrado!"));

        if(userUpdateDTO.name() != null && !userUpdateDTO.name().isBlank()){
            if (!userUpdateDTO.name().matches("^[a-zA-ZÀ-ÿ\\s]+$")) {
                throw new IllegalArgumentException("Nome não pode ter caracteres especiais!");
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
        return "Usuário atualizado!";
    }

    public String deleteUser(Integer id){
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado!"));
        userRepository.delete(user);
        return "Usuário deletado com sucesso!";
    }
}
