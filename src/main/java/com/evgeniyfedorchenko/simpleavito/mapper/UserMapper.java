package com.evgeniyfedorchenko.simpleavito.mapper;

import com.evgeniyfedorchenko.simpleavito.controller.UserController;
import com.evgeniyfedorchenko.simpleavito.dto.Register;
import com.evgeniyfedorchenko.simpleavito.dto.User;
import com.evgeniyfedorchenko.simpleavito.entity.UserEntity;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;

@Component
@AllArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

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

    public UserEntity fromRegister(Register register) {
        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(register.getUsername());
        userEntity.setFirstName(register.getFirstName());
        userEntity.setLastName(register.getLastName());
        userEntity.setPhone(register.getPhone());
        userEntity.setRole(register.getRole());
        userEntity.setPassword(passwordEncoder.encode(register.getPassword()));

        return userEntity;
    }

    protected String generateImageUrl(long id, @Nullable String... pathSegments) {

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance().pathSegment(String.valueOf(id));
        if (pathSegments != null) {
            Arrays.stream(pathSegments).forEach(uriComponentsBuilder::pathSegment);
        }
        return uriComponentsBuilder.build().toUriString();

    }

}
