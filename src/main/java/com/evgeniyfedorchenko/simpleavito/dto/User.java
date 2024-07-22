package com.evgeniyfedorchenko.simpleavito.dto;

import com.evgeniyfedorchenko.simpleavito.entity.Role;
import lombok.Data;

@Data
public class User {

    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
    private String image;

}
