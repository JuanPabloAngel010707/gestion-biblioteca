package com.biblioteca.gestion_biblioteca.service;

import java.util.Optional;
import java.util.List;
import com.biblioteca.gestion_biblioteca.model.Usuario;

public interface UsuarioService {
	List<Usuario> buscarTodos();
	Optional<Usuario> buscarPorDni(String dni);
    Usuario crearUsuario(Usuario usuario);
    void eliminarUsuario(String dni);
    Usuario actualizarUsuario(String dni, Usuario usuario);
    Boolean usuarioExistente(String dni);
}