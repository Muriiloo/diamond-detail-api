package com.diamonddetail.api.repository;

import com.diamonddetail.api.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Integer > {

}
