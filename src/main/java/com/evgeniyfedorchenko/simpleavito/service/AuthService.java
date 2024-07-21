package com.evgeniyfedorchenko.simpleavito.service;

import com.evgeniyfedorchenko.simpleavito.dto.Register;
import com.evgeniyfedorchenko.simpleavito.entity.Role;

import java.util.List;

public interface AuthService {
    boolean login(String userName, String password);

    boolean register(Register register);

    String getUsername();

    List<Role> getRoles();

    boolean isAdmin();
}
