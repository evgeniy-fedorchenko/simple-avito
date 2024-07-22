package com.evgeniyfedorchenko.simpleavito.repository;

import com.evgeniyfedorchenko.simpleavito.entity.AdEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdRepository extends JpaRepository<AdEntity, Long> {

    @NotNull
    Optional<AdEntity> findById(@NotNull Long aLong);

    @NotNull
    @Override
    <S extends AdEntity> S save(@NotNull S entity);

    @Override
    void deleteById(@NotNull Long aLong);
}
