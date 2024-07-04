package com.evgeniyfedorchenko.simpleavito.service;

import com.evgeniyfedorchenko.simpleavito.dto.Register;

public interface AuthService {
    boolean login(String userName, String password);

    boolean register(Register register);

    String getUsername();
}
