package com.evgeniyfedorchenko.simpleavito.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
public class Register {

    @Email
    private String username;
    @ToString.Exclude
    @Size(min = 8, max = 16)
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;

}
