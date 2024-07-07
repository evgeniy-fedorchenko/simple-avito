package com.evgeniyfedorchenko.simpleavito.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateOrUpdateComment {

    @NotBlank
    @Size(min = 8, max = 64)
    private String text;

}
