//package com.example.wallapod.config;
//
//import com.example.wallapod.service.CustomUserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests(authorize -> authorize
//                        .requestMatchers("/", "/login", "/registro", "/css/**", "/js/**", "/uploads/**", "/imagesProductos/**", "/productos/ver/{id}").permitAll() // Rutas públicas
//                        .requestMatchers("/productos/new", "/productos/edit/*", "/productos/delete/*").authenticated() // Rutas protegidas
//                        .anyRequest().authenticated() // El resto requiere autenticación
//                )
//                .formLogin(form -> form
//                        .loginPage("/login") // Página personalizada de login
//                        .loginProcessingUrl("/login") // URL de procesamiento del login
//                        .defaultSuccessUrl("/productos", true) // Redirigir a /productos después de un login exitoso
//                        .failureUrl("/login?error=true") // Si hay un error, redirigir a login con error
//                        .permitAll() // Permitir acceso público a la página de login
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/") // Redirigir al inicio después de hacer logout
//                        .permitAll() // Permitir logout público
//                );
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(passwordEncoder());
//        provider.setUserDetailsService(customUserDetailsService);
//        return provider;
//    }
//}

