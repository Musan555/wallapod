//package com.example.wallapod.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/login", "/productos/view/*", "/").permitAll()  // Permitimos acceso a login, vista de productos y raíz
//                        .requestMatchers("/categorias/new").hasRole("ADMIN")  // Solo los administradores pueden acceder a esta ruta
//                        .anyRequest().authenticated()  // El resto de las rutas requieren autenticación
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")  // Página personalizada de login
//                        .defaultSuccessUrl("/productos", true)  // Redirigir a productos después de login exitoso
//                        .failureUrl("/productos?errorLogin")  // Redirigir en caso de error de login
//                        .permitAll()  // Permitimos que todos accedan a la página de login
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/productos")  // Redirigir a productos después de logout
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")
//                        .permitAll()
//                );
//
//        return http.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails userDetails = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("user")
//                .roles("USER")
//                .build();
//
//        UserDetails adminDetails = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("admin")
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(userDetails, adminDetails);
//    }
//}
