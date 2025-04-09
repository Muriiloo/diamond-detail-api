package com.diamonddetail.api.record.users;

import com.diamonddetail.api.enums.UserType;


public record UserResponseDTO(String name, String email, UserType type) {

}
