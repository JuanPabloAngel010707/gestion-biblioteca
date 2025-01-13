package com.biblioteca.gestion_biblioteca.service;

import java.util.List;
import java.util.Optional;
import com.biblioteca.gestion_biblioteca.model.Libro;

public interface LibroService {
	List<Libro> buscarTodos();
    List<Libro> buscarPorTitulo(String titulo);
    List<Libro> buscarPorAutor(Long autorId);
    Optional<Libro> buscarPorIsbn(String isbn);
    Libro crearLibro(Libro libro, String nombreAutor);
    void eliminarLibro(String isbn);
    Libro actualizarLibro(Libro libro);
}
