package com.mibiblioteca.digital.service;

import com.mibiblioteca.digital.entity.Usuario;

public interface UsuarioService {
    Usuario guardar(Usuario usuario);
    Usuario buscarPorUsername(String username);
}