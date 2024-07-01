package com.evgeniyfedorchenko.simpleavito.service;

import org.springframework.web.multipart.MultipartFile;
import com.evgeniyfedorchenko.simpleavito.dto.NewPassword;
import com.evgeniyfedorchenko.simpleavito.dto.UpdateUser;

public interface UserService {


    void setPassword(NewPassword newPassword);

    void getUser();

    UpdateUser updateUser(UpdateUser updateUser);

    void updateUserImage(MultipartFile image);
}
