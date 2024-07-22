package com.evgeniyfedorchenko.simpleavito.dto;

import com.evgeniyfedorchenko.simpleavito.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
public class Register {

    @Email
    @Size(min = 4, max = 32)
    private String username;

    @ToString.Exclude
    @Size(min = 8, max = 16)
    private String password;

    @Size(min = 2, max = 16)
    private String firstName;

    @Size(min = 2, max = 16)
    private String lastName;

    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    private String phone;

    private Role role;

}
