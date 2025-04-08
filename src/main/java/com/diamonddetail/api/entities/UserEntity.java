package com.diamonddetail.api.entities;

import com.diamonddetail.api.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserType type;
}
