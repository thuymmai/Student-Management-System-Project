package com.project.sms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //implement @EnableWebSecurity to let Spring know I want to use Spring Security features
public class SpringConfig {

    //create an object
    private static final String[] PUBLIC_PATH = {
            "/login",
            "/css/**",
            "/images/**",
            "/js/**",
            "/error"
    };

    //create a method
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(PUBLIC_PATH).permitAll()
                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/dashboard", true)  //this means whenever user logs in, they will always see the dashboard
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll());
        return http.build();
    }

    //implement the interface named PasswordEncoder then name it passwordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {

        //whatever password I type in, it will be encrypted by the BCryptPasswordEncoder
        //then call the PasswordEncoder method in DataInitializer.java
        return new BCryptPasswordEncoder();
    }

}
