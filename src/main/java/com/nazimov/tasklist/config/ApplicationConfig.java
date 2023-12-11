package com.nazimov.tasklist.config;

import com.nazimov.tasklist.web.security.JwtTokenFilter;
import com.nazimov.tasklist.web.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor = @__(@Lazy))    //решение циклической зависимости аннотацией на аргументы коструктора @Lazy
public class ApplicationConfig {

    private final JwtTokenProvider tokenProvider;

    private final ApplicationContext applicationContext;

    /**
     * @return создает бин BCryptPasswordEncoder типа PasswordEncoder для хэширования паролей
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(configurer ->
                        configurer.authenticationEntryPoint((request, response, exception) -> {
                                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                                    response.getWriter().write("Unauthorized.");
                                })
                                .accessDeniedHandler((request, response, exception) -> {
                                    response.setStatus(HttpStatus.FORBIDDEN.value());
                                    response.getWriter().write("Unauthorized.");
                                })
                )
                .authorizeHttpRequests(configurer ->
                        configurer.requestMatchers("/api/v1/auth/**")
                                .permitAll()
                                //.requestMatchers("/swagger-ui/**")
                                //.permitAll()
                                //.requestMatchers("/v3/api-docs/**")
                                //.permitAll()
                                .anyRequest().authenticated()
                )
                .anonymous(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtTokenFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

}