package com.evgeniyfedorchenko.simpleavito.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
public class Login {

    @Email
    private String username;
    @ToString.Exclude
    @Size(min = 8, max = 16)
    private String password;

}
