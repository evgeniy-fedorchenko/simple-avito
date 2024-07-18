package com.evgeniyfedorchenko.simpleavito.controller;

import com.evgeniyfedorchenko.simpleavito.dto.NewPassword;
import com.evgeniyfedorchenko.simpleavito.dto.UpdateUser;
import com.evgeniyfedorchenko.simpleavito.dto.User;
import com.evgeniyfedorchenko.simpleavito.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:3000")
@Validated
@RestController
@RequestMapping(path = UserController.BASE_USER_URI)
@AllArgsConstructor
public class UserController {

    public static final String BASE_USER_URI = "/users";
    public static final String IMAGE_PATH_SEGMENT = "image";
    private final UserService userService;

    @PostMapping(path = "/set_password")
    public ResponseEntity<Void> setPassword(@RequestBody @Valid NewPassword newPassword) {
        return userService.setPassword(newPassword) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping(path = "/me")
    public ResponseEntity<User> getUser() {
        return ResponseEntity.ok(userService.getUser());
    }

    @PatchMapping(path = "/me")
    public ResponseEntity<UpdateUser> updateUser(@RequestBody @Valid UpdateUser updateUser) {
        return ResponseEntity.ok(userService.updateUser(updateUser));
    }

    @PatchMapping(path = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUserImage(@RequestPart MultipartFile image) {
        return userService.updateUserImage(image) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

}
