package com.example.wallapod.repository;

import com.example.wallapod.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar un usuario por email o nombre
    Usuario findByEmailOrNombre(String email, String nombre);

    // Puedes agregar más métodos personalizados si lo necesitas.
}
