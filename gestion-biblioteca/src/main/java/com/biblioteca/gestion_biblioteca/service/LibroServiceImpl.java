package com.biblioteca.gestion_biblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.biblioteca.gestion_biblioteca.model.Libro;
import com.biblioteca.gestion_biblioteca.model.Autor;
import com.biblioteca.gestion_biblioteca.repository.LibroRepository;



@Service
public class LibroServiceImpl implements LibroService {

    private final LibroRepository libroRepository;
    private final AutorService autorService;
    private final AutorLibroService autorLibroService;

    @Autowired
    public LibroServiceImpl(LibroRepository libroRepository, AutorService autorService, AutorLibroService autorLibroService) {
        this.libroRepository = libroRepository;
        this.autorService = autorService;
        this.autorLibroService = autorLibroService;

    }
    
    @Override
    public List<Libro> buscarTodos() {
        return libroRepository.findAll(); 
    }

    @Override
    public List<Libro> buscarPorTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    @Override
    public List<Libro> buscarPorAutor(Long autorId) {
    	return libroRepository.findByAutorLibrosAutorId(autorId);
    }

    @Override
    public Optional<Libro> buscarPorIsbn(String isbn) {
        
        return libroRepository.findById(isbn);
    }
    
    @Override
    public Libro crearLibro(Libro libro, String nombreAutor) {
    	Libro libroGuardado = libroRepository.save(libro);
    	Autor autor = autorService.crearAutorConLibro(nombreAutor);
        autorLibroService.crearAutorLibro(libroGuardado, autor); 
        return libroGuardado;
    }

    @Override
    public void eliminarLibro(String isbn) {
        
        libroRepository.deleteById(isbn);
    }
    
    @Override
    public Libro actualizarLibro(Libro libro) {
        return libroRepository.save(libro);
    }
}

