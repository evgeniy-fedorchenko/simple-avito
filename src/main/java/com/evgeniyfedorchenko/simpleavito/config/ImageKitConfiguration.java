package com.evgeniyfedorchenko.simpleavito.config;

import io.imagekit.sdk.ImageKit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageKitConfiguration {

    @Bean
    public ImageKit imageKit(ImageKitProperties properties) {
        ImageKit imageKit = ImageKit.getInstance();

        io.imagekit.sdk.config.Configuration configuration = new io.imagekit.sdk.config.Configuration(
                properties.getPublicKey(),
                properties.getPrivateKey(),
                properties.getUrlEndpoint()
        );

        imageKit.setConfig(configuration);
        return imageKit;

    }
}
