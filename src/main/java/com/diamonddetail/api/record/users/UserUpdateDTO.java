package com.diamonddetail.api.record.users;

import com.diamonddetail.api.enums.UserType;

public record UserUpdateDTO(String name, String email, String password, UserType type) {
}
