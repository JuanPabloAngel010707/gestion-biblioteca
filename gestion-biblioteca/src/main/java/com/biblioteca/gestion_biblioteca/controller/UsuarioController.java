package com.biblioteca.gestion_biblioteca.controller;

import com.biblioteca.gestion_biblioteca.model.Usuario;
import com.biblioteca.gestion_biblioteca.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodos() {
        List<Usuario> usuarios = usuarioService.buscarTodos();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/{dni}")
    public ResponseEntity<Usuario> obtenerPorDni(@PathVariable String dni) {
        Optional<Usuario> usuario = usuarioService.buscarPorDni(dni);
        return usuario.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
    	try {
    		Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
            return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    	} catch (IllegalArgumentException e) {
    		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    	}
    }

    @DeleteMapping("/{dni}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable String dni) {
    	try {
    		usuarioService.eliminarUsuario(dni);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    	} catch (IllegalArgumentException e) {
    		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    	}
        
    }
    
    @PutMapping("/{dni}")
    public ResponseEntity<?> editarUsuario(@PathVariable String dni, @RequestBody Usuario usuarioActualizado) {
       try {
            Usuario usuarioEditado = usuarioService.actualizarUsuario(dni, usuarioActualizado);
            return new ResponseEntity<>(usuarioEditado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
        	return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

