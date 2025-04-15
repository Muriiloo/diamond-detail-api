package com.diamonddetail.api.record.users;

import com.diamonddetail.api.entities.UserEntity;
import com.diamonddetail.api.enums.UserType;

public record UserCreateDTO(String name, String email, String password, UserType type) {
    public UserEntity toEntity(){
        return new UserEntity(null, name, email, password, type);
    }
}
