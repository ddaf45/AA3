package com.mibiblioteca.digital;

import com.mibiblioteca.digital.entity.Usuario;
import com.mibiblioteca.digital.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MiBibliotecaDigital {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(MiBibliotecaDigital.class, args);
    }

    @Bean
    public CommandLineRunner inicializarUsuarios() {
        return args -> {
            if (usuarioService.buscarPorUsername("admin") == null) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                usuarioService.guardar(admin);
                System.out.println("Usuario 'admin' creado.");
            }
        };
    }
}