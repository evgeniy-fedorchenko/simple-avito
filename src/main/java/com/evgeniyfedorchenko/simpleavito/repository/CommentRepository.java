package com.evgeniyfedorchenko.simpleavito.repository;

import com.evgeniyfedorchenko.simpleavito.entity.CommentEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @NotNull
    @Override
    Optional<CommentEntity> findById(@NotNull Long aLong);

    @NotNull
    @Override
    <S extends CommentEntity> S save(@NotNull S entity);

    @Override
    void deleteById(@NotNull Long aLong);

}
