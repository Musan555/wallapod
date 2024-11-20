package com.example.wallapod.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/productos", "/productos/view/*").permitAll() // Acceso público a estas rutas
                        .anyRequest().authenticated() // Todo lo demás requiere autenticación
                )
                .formLogin(
                        form -> form
                                .loginPage("/login") // Página personalizada para login
                                .defaultSuccessUrl("/productos") // Redirige a /productos tras login exitoso
                                .failureUrl("/login?error") // Redirige a /login con error si falla el login
                                .permitAll() // Permitir acceso al login para todos
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL para logout
                        .logoutSuccessUrl("/productos") // Redirige a productos tras logout
                        .invalidateHttpSession(true) // Invalida la sesión al hacer logout
                        .deleteCookies("JSESSIONID") // Elimina cookies de sesión
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("user")
                .roles("USER")
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
