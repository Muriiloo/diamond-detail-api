package com.diamonddetail.api.record.users;

import com.diamonddetail.api.enums.UserType;


public record UserResponseDTO(Integer id, String name, String email, UserType type) {

}
