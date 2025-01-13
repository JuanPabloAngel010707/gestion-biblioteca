package com.biblioteca.gestion_biblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.biblioteca.gestion_biblioteca.model.Libro;
import com.biblioteca.gestion_biblioteca.model.Autor;
import com.biblioteca.gestion_biblioteca.model.Prestamo;
import com.biblioteca.gestion_biblioteca.repository.LibroRepository;
import com.biblioteca.gestion_biblioteca.repository.PrestamoRepository;



@Service
public class LibroServiceImpl implements LibroService {

    private final LibroRepository libroRepository;
    private final AutorService autorService;
    private final AutorLibroService autorLibroService;
    private final PrestamoRepository prestamoRepository;

    @Autowired
    public LibroServiceImpl(LibroRepository libroRepository, AutorService autorService, AutorLibroService autorLibroService, PrestamoRepository prestamoRepository) {
        this.libroRepository = libroRepository;
        this.autorService = autorService;
        this.autorLibroService = autorLibroService;
        this.prestamoRepository = prestamoRepository;
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
    public List<Libro> buscarPorAutor(String autorNombre) {
    	return libroRepository.findByAutorLibrosAutorNombreContainingIgnoreCase(autorNombre);
    }

    @Override
    public Optional<Libro> buscarPorIsbn(String isbn) { 
        return libroRepository.findById(isbn);
    }
    
    @Override
    public Libro crearLibro(Libro libro, String nombreAutor) {
    	
    	if (libroExistente(libro.getIsbn())) {
    		throw new IllegalArgumentException("El libro con ISBN " + libro.getIsbn() + " ya existe."); 		
    	}
    	
    	
    	Libro libroGuardado = libroRepository.save(libro);
        Autor autor = autorService.crearAutorConLibro(nombreAutor);
        autorLibroService.crearAutorLibro(libroGuardado, autor); 
        return libroGuardado;	
    }

    @Override
    public void eliminarLibro(String isbn) {
    	if (!libroExistente(isbn)) {
    		throw new IllegalArgumentException("El libro con ISBN " + isbn + " no existe."); 		
    	}
    	
    	List<Prestamo> prestamosActivos = prestamoRepository.findByLibroIsbnAndEstado(isbn, "activo");
        if (!prestamosActivos.isEmpty()) {
            throw new IllegalArgumentException("No se puede eliminar el libro con ISBN " + isbn + 
                                               " porque tiene préstamos activos. ");
        }
        libroRepository.deleteById(isbn);
    }
    
    @Override
    public Libro actualizarLibro(String isbn, Libro libroActualizado) {
    	Optional<Libro> libroExistente = buscarPorIsbn(isbn);
    	if (libroExistente.isPresent()) {
    		
    	    Libro libro = libroExistente.get();

            libro.setTitulo(libroActualizado.getTitulo());
            libro.setEditorial(libroActualizado.getEditorial());
            libro.setEdicion(libroActualizado.getEdicion());
            libro.setAnoPublicacion(libroActualizado.getAnoPublicacion());
            
            return libroRepository.save(libro);
    	} else {
            throw new IllegalArgumentException("Libro con ISBN " + isbn + " no existe.");
        }
        
    }
    
    @Override
    public Boolean libroExistente(String isbn) {
    	Optional<Libro> libroExistente = buscarPorIsbn(isbn);
    	if (libroExistente.isPresent()) {
            return true; 
        }
    	return false;
    }
    
}

