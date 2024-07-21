package com.evgeniyfedorchenko.simpleavito.mapper;

import com.evgeniyfedorchenko.simpleavito.controller.UserController;
import com.evgeniyfedorchenko.simpleavito.dto.Register;
import com.evgeniyfedorchenko.simpleavito.dto.User;
import com.evgeniyfedorchenko.simpleavito.entity.UserEntity;
import jakarta.annotation.Nullable;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;

@Component
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        imports = {PasswordEncoder.class, UserController.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = PasswordEncoder.class
)
public interface UserMapper {

    @Mapping(target = "email", source = "username")
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(register.getPassword()))")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "imageCombinedId", ignore = true)
    @Mapping(target = "ads", ignore = true)
    UserEntity fromRegister(Register register);

    @Mapping(target = "image", expression = "java(this.generateImageUrl(userEntity, UserController.IMAGE_PATH_SEGMENT))")
    User toDto(UserEntity userEntity);

    default @Nullable String generateImageUrl(UserEntity userEntity, String... pathSegments) {
        if (userEntity.hasImage()) {
            return null;
        }
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance()
                .path(UserController.BASE_USER_URI)
                .pathSegment(String.valueOf(userEntity.getId()));
        if (pathSegments != null) {
            Arrays.stream(pathSegments).forEach(uriComponentsBuilder::pathSegment);
        }
        return uriComponentsBuilder.build().toUriString();
    }

}