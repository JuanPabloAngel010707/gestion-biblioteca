package com.biblioteca.gestion_biblioteca.repository;

import com.biblioteca.gestion_biblioteca.model.Autor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    
    List<Autor> findByNombreContainingIgnoreCase(String nombre);
}

