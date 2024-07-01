package com.evgeniyfedorchenko.simpleavito.repository;

import com.evgeniyfedorchenko.simpleavito.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
