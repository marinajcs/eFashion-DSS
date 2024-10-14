package com.dss.spring.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class SecurityConfig {

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
            		.requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")
	                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
	                .anyRequest().authenticated()
            )
            .formLogin(login -> login
	                .loginPage("/login")
	                .defaultSuccessUrl("/catalog", true)
	                .permitAll()
            )
            .logout(logout -> logout.permitAll());

        http.csrf(csrf -> csrf.disable());

        http.csrf(csrf -> csrf.disable());
        http.headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.sameOrigin())
        );
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

