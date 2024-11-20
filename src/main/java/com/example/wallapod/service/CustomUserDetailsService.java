//package com.example.wallapod.service;
//
//import com.example.wallapod.entity.Usuario;
//import com.example.wallapod.repository.UsuarioRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//public class CustomUserDetailsService implements UserDetailsService {
//    @Autowired
//    UsuarioRepository usuarioRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Usuario usuario = usuarioRepository.findByEmail(username)
//                .orElseThrow(() -> new UsernameNotFoundException("usuario no encontrado"));
//        UserDetails user = User.builder()
//                .username(usuario.getEmail())
//                .password(usuario.getPassword())
//                .build();
//        return user;
//    }
//}
