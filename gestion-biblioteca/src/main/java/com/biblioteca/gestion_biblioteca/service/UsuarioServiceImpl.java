package com.biblioteca.gestion_biblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

import com.biblioteca.gestion_biblioteca.model.Usuario;
import com.biblioteca.gestion_biblioteca.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    @Override
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> buscarPorDni(String dni) {
        return usuarioRepository.findById(dni);
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) {
    	if (usuarioExistente(usuario.getDni())) {
    		throw new IllegalArgumentException("El usuario con DNI " + usuario.getDni() + " ya existe.");   		
    	}
        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminarUsuario(String dni) {
    	if (!usuarioExistente(dni)) {
    		throw new IllegalArgumentException("El usuario con DNI " + dni + " no existe.");   		
    	}
        usuarioRepository.deleteById(dni);
    }
    
    @Override
    public Usuario actualizarUsuario(String dni, Usuario usuarioActualizado) {
    	
    	Optional<Usuario> usuarioExistente = buscarPorDni(dni);
    	if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();

            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setApellido(usuarioActualizado.getApellido());
            usuario.setTelefono(usuarioActualizado.getTelefono());
            usuario.setDireccion(usuarioActualizado.getDireccion());

         	return usuarioRepository.save(usuario);
    	} else {
            throw new IllegalArgumentException("El usuario con DNI " + dni + " no existe.");
        }
    }
    
    @Override
    public Boolean usuarioExistente(String dni) {
    	Optional<Usuario> usuarioExistente = buscarPorDni(dni);
    	if (usuarioExistente.isPresent()) {
            return true;
        }
    	return false;
    }
}

