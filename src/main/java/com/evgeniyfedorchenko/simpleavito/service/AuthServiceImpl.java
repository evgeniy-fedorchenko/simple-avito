package com.evgeniyfedorchenko.simpleavito.service;

import com.evgeniyfedorchenko.simpleavito.dto.Register;
import com.evgeniyfedorchenko.simpleavito.entity.UserEntity;
import com.evgeniyfedorchenko.simpleavito.mapper.UserMapper;
import com.evgeniyfedorchenko.simpleavito.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDetailsService manager;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public boolean login(String userName, String password) {

        UserDetails userDetails;
        try {
            userDetails = manager.loadUserByUsername(userName);
        } catch (UsernameNotFoundException _) {
            return false;
        }
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(Register register) {

        try {
            manager.loadUserByUsername(register.getUsername());
        } catch (UsernameNotFoundException _) {
            UserEntity userEntity = userMapper.fromRegister(register);
            userRepository.save(userEntity);
            return true;
        }

        return false;
    }

    @Override
    public String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
