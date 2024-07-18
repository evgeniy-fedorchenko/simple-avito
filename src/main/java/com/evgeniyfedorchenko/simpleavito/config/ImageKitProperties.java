package com.evgeniyfedorchenko.simpleavito.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@Component
@ConfigurationProperties(prefix = "image-kit")
public class ImageKitProperties {

    @NotBlank
    @URL(protocol = "https")
    private String urlEndpoint;

    @NotBlank
    @Pattern(regexp = "^private_.*")
    private String privateKey;

    @NotBlank
    @Pattern(regexp = "^public_.*")
    private String publicKey;

    @NotBlank
    private String cloudId;

}
