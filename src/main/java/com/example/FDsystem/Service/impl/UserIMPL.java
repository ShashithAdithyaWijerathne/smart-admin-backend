package com.example.FDsystem.Service.impl;

import com.example.FDsystem.DTO.LoginDTO;
import com.example.FDsystem.DTO.UserDTO;
import com.example.FDsystem.Entity.User;
import com.example.FDsystem.Repostory.UserRepo;
import com.example.FDsystem.Service.UserService;
import com.example.FDsystem.payloadresposne.LoginMesage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserIMPL implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public String addUser(UserDTO userDTO) {
        // Check if email already exists
        if (userRepo.findByEmail(userDTO.getEmail()) != null) {
            throw new RuntimeException("Email already exists");
        }

        // Create a new user
        User user = new User(
                userDTO.getUsername(),
                userDTO.getEmail(),
                userDTO.getPassword() // Store password as plain text
        );

        // Save the user
        userRepo.save(user);

        return user.getUsername();
    }

    @Override
    public LoginMesage loginUser(LoginDTO loginDTO) {
        User user = userRepo.findByEmail(loginDTO.getEmail());
        if (user != null) {
            String password = loginDTO.getPassword();
            String storedPassword = user.getPassword(); // Retrieve stored password (plain text)
            if (password.equals(storedPassword)) { // Compare passwords as plain text
                return new LoginMesage("Login Success", true);
            } else {
                return new LoginMesage("Password Not Match", false);
            }
        } else {
            return new LoginMesage("Email not exists", false);
        }
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public boolean validatePassword(User user, String password) {
        return user.getPassword().equals(password);
    }
}
