package com.evgeniyfedorchenko.simpleavito.repository;

import com.evgeniyfedorchenko.simpleavito.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
