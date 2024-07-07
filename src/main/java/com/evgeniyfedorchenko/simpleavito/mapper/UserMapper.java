package com.evgeniyfedorchenko.simpleavito.mapper;

import com.evgeniyfedorchenko.simpleavito.controller.UserController;
import com.evgeniyfedorchenko.simpleavito.dto.User;
import com.evgeniyfedorchenko.simpleavito.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class UserMapper {

    @Value("${server.port}")
    private int port;

    public User toDto(UserEntity userEntity) {
        User user = new User();

        user.setId(userEntity.getId());
        user.setEmail(userEntity.getEmail());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setPhone(userEntity.getPhone());
        user.setRole(userEntity.getRole());
        user.setImage(userEntity.hasImage() ? generateImageUrl(userEntity.getId()) : "no image");

        return user;
    }

    protected String generateImageUrl(long id) {

        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path(UserController.BASE_USER_URI)
                .pathSegment(String.valueOf(id), UserController.IMAGE_PATH_SEGMENT)

                .build()
                .toUri()
                .toString();
    }
}
