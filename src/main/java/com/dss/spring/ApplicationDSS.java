package com.dss.spring;

import com.dss.spring.repo.UserRepo;
import com.dss.spring.model.User;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ApplicationDSS extends SpringBootServletInitializer {
	private static final Logger log = LoggerFactory.getLogger(ApplicationDSS.class);
	public static void main(String[] args) {
		SpringApplication.run(ApplicationDSS.class, args);
	}

	@Bean
    public CommandLineRunner initData(UserRepo userRepository, PasswordEncoder passwordEncoder) {
	 return args -> {
            if (userRepository.findByUsername("admin") == null) {
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setPassword(passwordEncoder.encode("admin"));
                adminUser.setRole("ROLE_ADMIN");
                userRepository.save(adminUser);
            }

            if (userRepository.findByUsername("user") == null) {
                User normalUser = new User();
                normalUser.setUsername("user");
                normalUser.setPassword(passwordEncoder.encode("user"));
                normalUser.setRole("ROLE_USER");
                userRepository.save(normalUser);
            }
	 };
    }

}
