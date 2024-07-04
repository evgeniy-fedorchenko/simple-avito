package com.evgeniyfedorchenko.simpleavito.repository;

import com.evgeniyfedorchenko.simpleavito.dto.User;
import com.evgeniyfedorchenko.simpleavito.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

}
