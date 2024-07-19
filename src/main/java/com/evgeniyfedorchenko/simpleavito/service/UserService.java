package com.evgeniyfedorchenko.simpleavito.service;

import com.evgeniyfedorchenko.simpleavito.dto.NewPassword;
import com.evgeniyfedorchenko.simpleavito.dto.UpdateUser;
import com.evgeniyfedorchenko.simpleavito.dto.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface UserService {


    boolean setPassword(NewPassword newPassword);

    User getUser();

    UpdateUser updateUser(UpdateUser updateUser);

    boolean updateUserImage(MultipartFile image);

    Optional<byte[]> getImage(long id);
}
