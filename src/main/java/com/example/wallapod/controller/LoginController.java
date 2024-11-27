package com.example.wallapod.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // Mostrar el formulario de inicio de sesión
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Asegúrate de tener el archivo login.html
    }
}
