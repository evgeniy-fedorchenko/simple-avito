package com.evgeniyfedorchenko.simpleavito.dto;

import lombok.Data;
import lombok.ToString;

@Data
public class Login {

    private String username;
    @ToString.Exclude
    private String password;

}
