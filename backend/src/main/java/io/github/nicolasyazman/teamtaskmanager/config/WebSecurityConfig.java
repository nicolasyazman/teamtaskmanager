package io.github.nicolasyazman.teamtaskmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class WebSecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for APIs (unless you use cookies)
            .cors(cors -> {})
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS)) // No session (pure REST)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("user").hasRole("ADMIN") // Allow anyone to call /user
                .requestMatchers("user/{id}").hasRole("ADMIN")
                .requestMatchers("auth/login").permitAll() 
                .anyRequest().authenticated()               // Other endpoints need authentication
            );

        return http.build();
    }
}
