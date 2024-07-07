package com.evgeniyfedorchenko.simpleavito.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUser {

    @Size(min = 2, max = 16)
    private String firstName;

    @Size(min = 2, max = 16)
    private String lastName;

    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    private String phone;

}
