package com.evgeniyfedorchenko.simpleavito.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateOrUpdateAd {

    @Size(min = 4, max = 32)
    private String title;

    @Min(0)
    @Max(10_000_000)
    private int price;

    @Size(min = 8, max = 64)
    private String description;

}
