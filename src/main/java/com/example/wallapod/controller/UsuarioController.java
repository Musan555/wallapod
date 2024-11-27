package com.example.wallapod.controller;

import com.example.wallapod.entity.Usuario;
import com.example.wallapod.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Mostrar formulario de registro
    @GetMapping("/registro")
    public String mostrarRegistroForm(Model model) {
        model.addAttribute("usuario", new Usuario()); // Crear un nuevo objeto Usuario para el formulario
        return "registro"; // Vista de registro
    }

    // Manejar el envío del formulario de registro
    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario) {
        // Guardar el usuario en la base de datos
        usuarioRepository.save(usuario);
        return "redirect:/productos"; // Redirigir a la página de login después del registro
    }

    // Mostrar lista de usuarios registrados
    @GetMapping("/usuarios")
    public String mostrarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll()); // Obtener todos los usuarios
        return "usuarios"; // Vista de la lista de usuarios
    }

    // Mostrar detalles de un usuario específico (si es necesario)
    @GetMapping("/usuarios/{id}")
    public String mostrarDetallesUsuario(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioRepository.findById(id).orElse(null)); // Obtener usuario por ID
        return "usuarioDetalles"; // Vista de detalles del usuario
    }
}
