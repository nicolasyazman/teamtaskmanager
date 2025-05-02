package io.github.nicolasyazman.teamtaskmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.github.nicolasyazman.teamtaskmanager.security.JwtAuthFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class WebSecurityConfig {

	private final JwtAuthFilter jwtAuthFilter;
	
	public WebSecurityConfig(JwtAuthFilter jwtAuthFilter) {
		this.jwtAuthFilter = jwtAuthFilter;
	}
	
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
            	.requestMatchers("/api/health").permitAll()
            	.requestMatchers(HttpMethod.POST, "/user").permitAll() // Allows to create users (POST)
            	.requestMatchers(HttpMethod.GET, "/user/**").hasRole("ADMIN") // But not to list created users
                .requestMatchers("/user/{id}").hasRole("ADMIN")
                .requestMatchers("/auth/login").permitAll()
                .requestMatchers("/project/mine").permitAll()
                .anyRequest().authenticated()               // Other endpoints need authentication
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    

        return http.build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
