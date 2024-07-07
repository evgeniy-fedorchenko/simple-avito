package com.evgeniyfedorchenko.simpleavito.repository;

import com.evgeniyfedorchenko.simpleavito.entity.AdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdRepository extends JpaRepository<AdEntity, Long> {
}
