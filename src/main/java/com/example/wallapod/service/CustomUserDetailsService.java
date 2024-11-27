//package com.example.wallapod.service;
//
//import com.example.wallapod.entity.Usuario;
//import com.example.wallapod.repository.UsuarioRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UsuarioRepository usuarioRepository;
//
//    @Autowired
//    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
//        this.usuarioRepository = usuarioRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Usuario usuario = usuarioRepository.findByEmail(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
//        return User.withUsername(usuario.getEmail())
//                .password(usuario.getPassword())
//                .roles(usuario.getRol()) // Asignar el rol del usuario desde la base de datos
//                .build();
//    }
//}


