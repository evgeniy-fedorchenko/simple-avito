package com.evgeniyfedorchenko.simpleavito.dto;

import lombok.Data;

@Data
public class Ad {

    private long author;
    private String image;
    private long pk;
    private long price;
    private String title;
}
