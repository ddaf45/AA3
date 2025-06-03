package com.mibiblioteca.digital.controller;

import com.mibiblioteca.digital.entity.Usuario;
import com.mibiblioteca.digital.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        usuarioService.guardar(usuario);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "login";
    }

    @GetMapping("/")
    public String paginaPrincipal() {
        return "index"; // Puedes crear una página de inicio
    }
}