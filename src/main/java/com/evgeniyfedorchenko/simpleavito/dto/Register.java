package com.evgeniyfedorchenko.simpleavito.dto;

import lombok.Data;
import lombok.ToString;

@Data
public class Register {

    private String username;
    @ToString.Exclude
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;

}
