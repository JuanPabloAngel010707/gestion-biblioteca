package com.biblioteca.gestion_biblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import com.biblioteca.gestion_biblioteca.model.Prestamo;
import com.biblioteca.gestion_biblioteca.model.Libro;
import com.biblioteca.gestion_biblioteca.model.Usuario;
import com.biblioteca.gestion_biblioteca.repository.PrestamoRepository;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final LibroService libroService;
    private final UsuarioService usuarioService;

    @Autowired
    public PrestamoServiceImpl(PrestamoRepository prestamoRepository, LibroService libroService, UsuarioService usuarioService) {
        this.prestamoRepository = prestamoRepository;
        this.libroService = libroService;
        this.usuarioService = usuarioService;
    }
    
    @Override
    public List<Prestamo> buscarTodos() {
        return prestamoRepository.findAll(); 
    }

    @Override
    public List<Prestamo> buscarPorIsbnLibro(String isbnLibro) {
        return prestamoRepository.findByLibroIsbn(isbnLibro);
    }

    @Override
    public List<Prestamo> buscarPorDniUsuario(String dniUsuario) {
        return prestamoRepository.findByUsuarioDni(dniUsuario);
    }

    @Override
    public Optional<Prestamo> buscarPorId(Long id) {
        return prestamoRepository.findById(id);
    }

    @Override
    public Prestamo crearPrestamo(String isbnLibro, String dniUsuario) {
    	
    	Libro libro = libroService.buscarPorIsbn(isbnLibro).orElseThrow(() ->
        new IllegalArgumentException("El libro con ISBN " + isbnLibro + " no existe."));
        Usuario usuario = usuarioService.buscarPorDni(dniUsuario).orElseThrow(() ->
        new IllegalArgumentException("El usuario con DNI " + dniUsuario + " no existe."));
        
    	if (!prestamoRepository.findByLibroIsbnAndEstado(isbnLibro, "activo").isEmpty()) {
    		throw new IllegalArgumentException("El libro con ISBN " + isbnLibro + " ya esta prestado.");
    	}
    	List<Prestamo> prestamosDelUsuario = prestamoRepository.findByUsuarioDniAndEstado(dniUsuario, "activo");
        if (prestamosDelUsuario.size() >= 5) {
            throw new IllegalArgumentException("El usuario con DNI " + dniUsuario + " ya tiene 5 préstamos activos.");
        }
        
        Prestamo prestamo = new Prestamo();
        prestamo.setLibro(libro);
        prestamo.setUsuario(usuario);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setEstado("activo");
        
        return prestamoRepository.save(prestamo);
    }

    @Override
    public void eliminarPrestamo(Long id) {
    	if (!prestamoExistente(id)) {
    		throw new IllegalArgumentException("El prestamo con ID " + id + " no existe."); 		
    	} 
        prestamoRepository.deleteById(id);
    }
    
    @Override
    public Boolean prestamoExistente(Long id) {
    	Optional<Prestamo> prestamoExistente = buscarPorId(id);
    	if (prestamoExistente.isPresent()) {
            return true; 
        }
    	return false;
    }
}

