package com.example.FDsystem;

import com.example.FDsystem.Config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication()
@CrossOrigin
public class FDsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FDsystemApplication.class, args);
	}

}
