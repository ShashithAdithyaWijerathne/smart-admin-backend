package com.example.FDsystem.Controller;

import com.example.FDsystem.DTO.LoginDTO;
import com.example.FDsystem.DTO.UserDTO;
import com.example.FDsystem.Service.UserService;
import com.example.FDsystem.payloadresposne.LoginMesage;
import com.example.FDsystem.response.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
@RestController
@CrossOrigin
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/save")
    public ResponseEntity<String> saveUser(@RequestBody UserDTO userDTO) {
        try {
            String id = userService.addUser(userDTO);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while saving user");
        }
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
        try {
            LoginMesage loginResponse = userService.loginUser(loginDTO);

            // Create expiration date 15 minutes from now
            Date expirationDate = new Date(System.currentTimeMillis() + 15 * 60 * 1000);

            // Create JWT token
            String token = Jwts.builder()
                        // Use username as subject
                    .claim("email", loginDTO.getEmail())
                    .claim("created_at", new Date())
                    .setIssuedAt(new Date())
                    .setExpiration(expirationDate) // Set expiration date
                    .signWith(SignatureAlgorithm.HS256, "smtfd") // Replace "yourSecretKey" with your actual secret key
                    .compact();

            return ResponseEntity.ok(new AuthResponse(token, loginResponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Incorrect username or password");
        }
    }
}
