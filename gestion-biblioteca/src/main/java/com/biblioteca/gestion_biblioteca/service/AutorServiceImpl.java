package com.biblioteca.gestion_biblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.biblioteca.gestion_biblioteca.model.Autor;
import com.biblioteca.gestion_biblioteca.repository.AutorRepository;
import jakarta.validation.Valid;


@Service
public class AutorServiceImpl implements AutorService {

    private final AutorRepository autorRepository;

    @Autowired
    public AutorServiceImpl(AutorRepository autorRepository, AutorLibroService autorLibroService) {
        this.autorRepository = autorRepository;
    }
    
    @Override
    public List<Autor> buscarTodos() {
        return autorRepository.findAll(); 
    }

    @Override
    public List<Autor> buscarPorNombre(String nombre) {
        return autorRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public Optional<Autor> buscarPorId(Long id) {
        return autorRepository.findById(id);
    }

    @Override
    public Autor crearAutor(@Valid Autor autor) {
    	return autorRepository.save(autor); 		
    }

    @Override
    public void eliminarAutor(Long id) {
    	if (!autorExistente(id)) {
    		throw new IllegalArgumentException("El autor con ID " + id + " no existe.");	
    	}
        autorRepository.deleteById(id);
    }
    
    @Override
    public Autor crearAutorConLibro(String nombreAutor) {
    	
    	List<Autor> autores = buscarPorNombre(nombreAutor);
    	
    	if (!autores.isEmpty()) {
            return autores.get(0);
        } else {        	
            Autor nuevoAutor = new Autor();
            nuevoAutor.setNombre(nombreAutor);

            return crearAutor(nuevoAutor);
        } 		
    }
    
    @Override    
    public Boolean autorExistente(Long id) {
    	Optional<Autor> autorExistente = buscarPorId(id);
    	if (autorExistente.isPresent()) {
    		return true;      
        }	
    	return false;
    }
}

