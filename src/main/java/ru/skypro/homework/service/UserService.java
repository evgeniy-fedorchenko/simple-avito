package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;

public interface UserService {


    void setPassword(NewPassword newPassword);

    void getUser();

    UpdateUser updateUser(UpdateUser updateUser);

    void updateUserImage(MultipartFile image);
}
