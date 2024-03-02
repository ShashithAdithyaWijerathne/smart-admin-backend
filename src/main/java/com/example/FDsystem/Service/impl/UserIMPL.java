package com.example.FDsystem.Service.impl;

import com.example.FDsystem.DTO.LoginDTO;
import com.example.FDsystem.DTO.UserDTO;
import com.example.FDsystem.Entity.User;
import com.example.FDsystem.Repostory.UserRepo;
import com.example.FDsystem.Service.UserService;
import com.example.FDsystem.payloadresposne.LoginMesage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserIMPL implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String addUser(UserDTO userDTO) {
        User user = new User(
                userDTO.getUserid(),
                userDTO.getUsername(),
                userDTO.getEmail(),
                passwordEncoder.encode(userDTO.getPassword())
        );
        userRepo.save(user);
        return user.getUsername();
    }

    @Override
    public LoginMesage loginUser(LoginDTO loginDTO) {
        User user = userRepo.findByEmail(loginDTO.getEmail());
        if (user != null) {
            String password = loginDTO.getPassword();
            String encodedPassword = user.getPassword();
            boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                return new LoginMesage("Login Success", true);
            } else {
                return new LoginMesage("Password Not Match", false);
            }
        } else {
            return new LoginMesage("Email not exists", false);
        }
    }
}
