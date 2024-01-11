package com.pfc2.weather.resourceserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import com.pfc2.weather.exception.CustomAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class DirectlyConfiguredJwkSetUri {
	
	@Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
	private String jwkUri; 
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
        http.csrf((csrf) -> csrf.disable())
        	.headers((headers) -> headers.frameOptions(Customizer.withDefaults()).disable())
            .authorizeHttpRequests(authorize -> authorize
            	.requestMatchers("/sys/status", "/swagger-ui.html", "/swagger-ui/**", "/api-docs/**", "/h2-console", "/h2-console/**").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .jwkSetUri(jwkUri)
                ).authenticationEntryPoint(authenticationEntryPointHandler())
            );
        return http.build();
    }
	
	@Bean
    public AuthenticationEntryPoint authenticationEntryPointHandler() {
        return new CustomAuthenticationEntryPoint();
    }
	
}

