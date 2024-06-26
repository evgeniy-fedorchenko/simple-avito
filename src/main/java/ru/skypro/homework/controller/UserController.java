package ru.skypro.homework.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.service.UserService;

@Validated
@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/set_password")
    public ResponseEntity<Void> setPassword(@RequestBody @Valid NewPassword newPassword) {
        userService.setPassword(newPassword);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/me")
    public ResponseEntity<Void> getUser() {
        userService.getUser();
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/me")
    public ResponseEntity<UpdateUser> getUser(@RequestBody @Valid UpdateUser updateUser) {
        return ResponseEntity.ok(userService.updateUser(updateUser));
    }

    @PatchMapping(path = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUserImage(@RequestBody MultipartFile image) {
        userService.updateUserImage(image);
        return ResponseEntity.ok().build();
    }

}
