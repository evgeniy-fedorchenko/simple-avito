package com.evgeniyfedorchenko.simpleavito.service;

import com.evgeniyfedorchenko.simpleavito.dto.NewPassword;
import com.evgeniyfedorchenko.simpleavito.dto.UpdateUser;
import com.evgeniyfedorchenko.simpleavito.dto.User;
import com.evgeniyfedorchenko.simpleavito.entity.UserEntity;
import com.evgeniyfedorchenko.simpleavito.exception.ImageParsedException;
import com.evgeniyfedorchenko.simpleavito.mapper.UserMapper;
import com.evgeniyfedorchenko.simpleavito.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public boolean setPassword(NewPassword newPassword) {
        UserEntity userEntity = userRepository.findByEmail(authService.getUsername());

        if (passwordEncoder.matches(newPassword.getCurrentPassword(), userEntity.getPassword())) {
            throw new IllegalArgumentException("Invalid current password");
        }
        userEntity.setPassword(passwordEncoder.encode(newPassword.getNewPassword()));
        userRepository.save(userEntity);
        log.info("Password of user {} updated", userEntity);

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser() {
        return userMapper.toDto(userRepository.findByEmail(authService.getUsername()));
    }

    @Override
    @Transactional
    public UpdateUser updateUser(UpdateUser updateUser) {
        UserEntity userEntity = userRepository.findByEmail(authService.getUsername());

        userEntity.setFirstName(updateUser.getFirstName());
        userEntity.setLastName(updateUser.getLastName());
        userEntity.setPhone(updateUser.getPhone());

        UserEntity savedUser = userRepository.save(userEntity);
        log.debug("Updated user {}", savedUser);
        return updateUser;
    }

    @Override
    @Transactional
    public boolean updateUserImage(MultipartFile image) {
        UserEntity userEntity = userRepository.findByEmail(authService.getUsername());

        try {
            userEntity.setImage(image.getBytes());
            UserEntity savedUser = userRepository.save(userEntity);
            log.debug("Updated image of user {}", savedUser);
            return true;

        } catch (IOException _) {
            throw new ImageParsedException("Image could not be parsed");
        }
    }
}
