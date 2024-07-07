package com.evgeniyfedorchenko.simpleavito.mapper;

import com.evgeniyfedorchenko.simpleavito.controller.UserController;
import com.evgeniyfedorchenko.simpleavito.dto.User;
import com.evgeniyfedorchenko.simpleavito.entity.UserEntity;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;

@Component
public class UserMapper {

    public User toDto(UserEntity userEntity) {
        User user = new User();

        user.setId(userEntity.getId());
        user.setEmail(userEntity.getEmail());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setPhone(userEntity.getPhone());
        user.setRole(userEntity.getRole());

        user.setImage(userEntity.hasImage()
                ? generateImageUrl(userEntity.getId(), UserController.IMAGE_PATH_SEGMENT)
                : null
        );

        return user;
    }

    protected String generateImageUrl(long id, @Nullable String... pathSegments) {

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance().pathSegment(String.valueOf(id));
        if (pathSegments != null) {
            Arrays.stream(pathSegments).forEach(uriComponentsBuilder::pathSegment);
        }
        return uriComponentsBuilder.build().toUriString();

    }

}
