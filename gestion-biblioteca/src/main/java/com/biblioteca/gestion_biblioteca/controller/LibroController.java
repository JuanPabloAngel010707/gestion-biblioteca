package com.biblioteca.gestion_biblioteca.controller;

import com.biblioteca.gestion_biblioteca.model.Libro;
import com.biblioteca.gestion_biblioteca.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/autor/{autorId}")
    public ResponseEntity<List<Libro>> buscarPorAutor(@PathVariable Long autorId) {
        List<Libro> libros = libroService.buscarPorAutor(autorId);
        if (libros.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
        return new ResponseEntity<>(libros, HttpStatus.OK);   
    }
    
    @PostMapping("/crear")
    public ResponseEntity<Libro> crearLibro(@RequestBody Libro libro,
                                            @RequestParam String nombreAutor) {
        Libro nuevoLibro = libroService.crearLibro(libro, nombreAutor);
        return new ResponseEntity<>(nuevoLibro, HttpStatus.CREATED);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable String isbn) {
        libroService.eliminarLibro(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Libro>> buscarPorTitulo(@RequestParam String titulo) {
        List<Libro> libros = libroService.buscarPorTitulo(titulo);
        return new ResponseEntity<>(libros, HttpStatus.OK);
    }
    
    @PutMapping("/{isbn}")
    public ResponseEntity<Libro> editarLibro(@PathVariable String isbn, @RequestBody Libro libroActualizado) {
        Optional<Libro> libroExistente = libroService.buscarPorIsbn(isbn);

        if (libroExistente.isPresent()) {
            Libro libro = libroExistente.get();

            libro.setTitulo(libroActualizado.getTitulo());
            libro.setEditorial(libroActualizado.getEditorial());
            libro.setEdicion(libroActualizado.getEdicion());
            libro.setAnoPublicacion(libroActualizado.getAnoPublicacion());
            libro.setCantidad(libroActualizado.getCantidad());

            Libro libroEditado = libroService.actualizarLibro(libro);
            return new ResponseEntity<>(libroEditado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

