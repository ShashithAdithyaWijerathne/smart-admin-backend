package com.example.FDsystem.Service;

import com.example.FDsystem.DTO.LoginDTO;
import com.example.FDsystem.DTO.UserDTO;
import com.example.FDsystem.Entity.User;
import com.example.FDsystem.payloadresposne.LoginMesage;

public interface UserService {
    String addUser(UserDTO userDTO);
    LoginMesage loginUser(LoginDTO loginDTO);

    User findUserByEmail(String email);

    boolean validatePassword(User user, String password);
}
