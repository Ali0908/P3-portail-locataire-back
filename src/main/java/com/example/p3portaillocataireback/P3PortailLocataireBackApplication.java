package com.example.p3portaillocataireback;

import com.example.p3portaillocataireback.auth.RegisterRequest;
import com.example.p3portaillocataireback.user.Role;
import com.example.p3portaillocataireback.auth.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class P3PortailLocataireBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(P3PortailLocataireBackApplication.class, args);
	}
		@Bean
	public CommandLineRunner commandLineRunner(
			AuthService service
	) {
		return args -> {
			var admin = RegisterRequest.builder()
					.name("admin")
					.email("admin@mail.com")
					.password("password")
					.role(Role.ADMIN)
					.build();
			System.out.println("Admin token: " + service.register(admin).getAccessToken());

			var user = RegisterRequest.builder()
					.name("user")
					.email("user@mail.com")
					.password("password")
					.role(Role.USER)
					.build();
			System.out.println("User token: " + service.register(user).getAccessToken());

		};
	}

}
