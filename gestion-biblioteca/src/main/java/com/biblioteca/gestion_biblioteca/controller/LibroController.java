package com.biblioteca.gestion_biblioteca.controller;

import com.biblioteca.gestion_biblioteca.model.Libro;
import com.biblioteca.gestion_biblioteca.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroService libroService;

    @Autowired
    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public ResponseEntity<List<Libro>> obtenerTodos() {
        List<Libro> libros = libroService.buscarTodos();
        return new ResponseEntity<>(libros, HttpStatus.OK);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Libro> obtenerPorIsbn(@PathVariable String isbn) {
        Optional<Libro> libro = libroService.buscarPorIsbn(isbn);
        return libro.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/autor/{autorNombre}")
    public ResponseEntity<List<Libro>> buscarPorAutor(@PathVariable String autorNombre) {
        List<Libro> libros = libroService.buscarPorAutor(autorNombre);
        if (libros.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
        return new ResponseEntity<>(libros, HttpStatus.OK);   
    }
    
    @PostMapping("/crear")
    public ResponseEntity<?> crearLibro(@Valid @RequestBody Libro libro, @RequestParam String nombreAutor) {
        try {
        	Libro nuevoLibro = libroService.crearLibro(libro, nombreAutor);
            return new ResponseEntity<>(nuevoLibro, HttpStatus.CREATED);   	
        } catch (IllegalArgumentException e) {
        	return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<?> eliminarLibro(@PathVariable String isbn) {
    	try {
    		libroService.eliminarLibro(isbn);
    		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    	} catch (IllegalArgumentException e) {
        	return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }   
    }

    @GetMapping("/buscar/{titulo}")
    public ResponseEntity<List<Libro>> buscarPorTitulo(@PathVariable String titulo) {
        List<Libro> libros = libroService.buscarPorTitulo(titulo);
        if (libros.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
        return new ResponseEntity<>(libros, HttpStatus.OK); 
    }
    
    @PutMapping("/{isbn}")
    public ResponseEntity<?> editarLibro(@Valid @PathVariable String isbn, @RequestBody Libro libroActualizado) {
        try {
            Libro libroEditado = libroService.actualizarLibro(isbn, libroActualizado);
            return new ResponseEntity<>(libroEditado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
        	return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

