package com.example.wallapod.service;

import com.example.wallapod.entity.Usuario;
import com.example.wallapod.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Buscar usuario por email o nombre
    public Usuario findByEmailOrNombre(String identifier) {
        return usuarioRepository.findByEmailOrNombre(identifier, identifier);
    }

    // Puedes agregar más métodos de servicio si es necesario.
}
