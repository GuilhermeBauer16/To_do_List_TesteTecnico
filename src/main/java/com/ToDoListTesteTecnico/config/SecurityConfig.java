package com.ToDoListTesteTecnico.config;


import com.ToDoListTesteTecnico.filters.CsrfCookieFilter;
import com.ToDoListTesteTecnico.filters.JwtValidationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {


    private static final String[] CSRF_IGNORE_REQUEST_MATCHER = {"/user/**","/login/**","/task/**","/subtask/**"};
    private static final String[] USER_RESOURCES = {"/task/**","/subtask/**"};
    private static final String ROLE_USER = "USER";


    @Bean
//    @Autowired
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtValidationFilter jwtValidationFilter) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(csrf -> csrf.csrfTokenRequestHandler(requestHandler)
                        .ignoringRequestMatchers(CSRF_IGNORE_REQUEST_MATCHER)
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(jwtValidationFilter, BasicAuthenticationFilter.class)
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authorizeHttpRequests -> authorizeHttpRequests

                                .requestMatchers(USER_RESOURCES).hasRole(ROLE_USER)
                                .anyRequest().permitAll()


                )


                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource =
                new UrlBasedCorsConfigurationSource();

        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);
        return urlBasedCorsConfigurationSource;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();

    }
}
