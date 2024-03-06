package com.example.FDsystem.Controller;

import com.example.FDsystem.DTO.LoginDTO;
import com.example.FDsystem.DTO.UserDTO;
import com.example.FDsystem.Entity.User;
import com.example.FDsystem.Service.UserService;
import com.example.FDsystem.payloadresposne.LoginMesage;
import com.example.FDsystem.response.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.text.SimpleDateFormat;
@RestController
@CrossOrigin
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveUser(@RequestBody UserDTO userDTO) {
        try {
            String id = userService.addUser(userDTO);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while saving user");
        }
    }

    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        try {
            // Retrieve user details from the database
            User user = userService.findUserByEmail(loginDTO.getEmail());

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: User not found");
            }

            // Validate password
            if (!userService.validatePassword(user, loginDTO.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Incorrect password");
            }

            // Get the last active time from the session or create one if it doesn't exist
            HttpSession session = request.getSession();
            Long lastActiveTime = (Long) session.getAttribute("lastActiveTime");
            if (lastActiveTime == null) {
                lastActiveTime = System.currentTimeMillis();
                session.setAttribute("lastActiveTime", lastActiveTime);
            }

            // Check the time elapsed since the last activity
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - lastActiveTime;
            if (elapsedTime > 15 * 60 * 1000) { // 15 minutes
                // If more than 15 minutes have passed since the last activity, invalidate the session
                session.invalidate();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session expired due to inactivity");
            }

            // Update the last active time
            session.setAttribute("lastActiveTime", currentTime);

            // Create expiration date 15 minutes from the current time
            Date expirationDate = new Date(currentTime + 15 * 60 * 1000);
            Date createdAt = new Date();

            // Format dates
            SimpleDateFormat sdf = new SimpleDateFormat("dd:MMM:yyyy:HH:mm:ss");
            String createdAtFormatted = sdf.format(createdAt);

            // Create JWT token with additional claims
            String token = Jwts.builder()
                    .claim("userId", user.getUserid())
                    .claim("username", user.getUsername())
                    .claim("email", user.getEmail())
                    .claim("created_at", createdAtFormatted)
                    .setIssuedAt(createdAt)
                    .setExpiration(expirationDate)
                    .signWith(SignatureAlgorithm.HS256, "smtfd")
                    .compact();

            // Create the response object with the token
            AuthResponse authResponse = new AuthResponse(token, new LoginMesage("Login successful", true), user.getUsername(), user.getUserid(), createdAt, expirationDate);

            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

}
