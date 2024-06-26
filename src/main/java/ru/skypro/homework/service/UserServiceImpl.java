package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;

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
