package com.biblioteca.gestion_biblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.biblioteca.gestion_biblioteca.repository.AutorLibroRepository;
import com.biblioteca.gestion_biblioteca.model.Autor;
import com.biblioteca.gestion_biblioteca.model.AutorLibro;
import com.biblioteca.gestion_biblioteca.model.AutorLibroId;
import com.biblioteca.gestion_biblioteca.model.Libro;

@Service
public class AutorLibroServiceImpl implements AutorLibroService {

    private final AutorLibroRepository autorLibroRepository;

    @Autowired
    public AutorLibroServiceImpl(AutorLibroRepository autorLibroRepository) {
        this.autorLibroRepository = autorLibroRepository;
    }
    
    @Transactional
    @Override
    public AutorLibro crearAutorLibro(Libro libro, Autor autor) {
    	
    	AutorLibroId autorLibroId = new AutorLibroId(libro.getIsbn(), autor.getId());
        
        AutorLibro autorLibro = new AutorLibro();
        
        autorLibro.setId(autorLibroId);
        autorLibro.setLibro(libro);
        autorLibro.setAutor(autor);
        
        return autorLibroRepository.save(autorLibro);
    }

    @Override
    public void eliminarAutorLibro(AutorLibroId autorLibroId) {
        autorLibroRepository.deleteById(autorLibroId);
    }
}

