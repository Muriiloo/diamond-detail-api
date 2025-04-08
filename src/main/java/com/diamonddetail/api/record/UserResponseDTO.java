package com.diamonddetail.api.record;

import com.diamonddetail.api.enums.UserType;


public record UserResponseDTO(String name, String email, UserType type) {

}
