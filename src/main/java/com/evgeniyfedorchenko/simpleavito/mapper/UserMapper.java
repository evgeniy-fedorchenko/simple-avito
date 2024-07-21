package com.evgeniyfedorchenko.simpleavito.mapper;

import com.evgeniyfedorchenko.simpleavito.controller.UserController;
import com.evgeniyfedorchenko.simpleavito.dto.Register;
import com.evgeniyfedorchenko.simpleavito.dto.User;
import com.evgeniyfedorchenko.simpleavito.entity.UserEntity;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;

@Component
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        imports = UserController.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
@NoArgsConstructor  // Такой набор аннотаций чисто для теста, чтобы все разрешить и не париться. Потом конечно это уберу и оставлю минимум
@Data
public abstract class UserMapper {


    private PasswordEncoder passwordEncoder;

    @Mapping(target = "email", source = "username")
    @Mapping(target = "password", qualifiedByName = "encodePassword")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "imageCombinedId", ignore = true)
    @Mapping(target = "ads", ignore = true)
    public abstract UserEntity fromRegister(Register register);

    @Mapping(target = "image", expression = "java(this.generateImageUrl(userEntity))")
    public abstract User toDto(UserEntity userEntity);

    @Named("encodePassword")
    String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Named("generateImageUrl")
    String generateImageUrl(UserEntity userEntity) {
        return this.generateImageUrl(userEntity, UserController.IMAGE_PATH_SEGMENT);
    }

    private @Nullable String generateImageUrl(UserEntity userEntity, String... pathSegments) {
        if (!userEntity.hasImage()) {
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