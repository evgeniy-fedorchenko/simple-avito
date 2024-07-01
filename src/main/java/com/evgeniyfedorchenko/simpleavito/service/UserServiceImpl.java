package com.evgeniyfedorchenko.simpleavito.service;

import com.evgeniyfedorchenko.simpleavito.dto.NewPassword;
import com.evgeniyfedorchenko.simpleavito.dto.UpdateUser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public void setPassword(NewPassword newPassword) {
    }

    @Override
    public void getUser() {
    }

    @Override
    public UpdateUser updateUser(UpdateUser updateUser) {
        return new UpdateUser();
    }

    @Override
    public void updateUserImage(MultipartFile image) {
    }
}
