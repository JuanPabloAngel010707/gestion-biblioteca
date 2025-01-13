package com.biblioteca.gestion_biblioteca.repository;


import com.biblioteca.gestion_biblioteca.model.Libro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, String> {

    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    List<Libro> findByAutorLibrosAutorId(Long autorId);
}

