package com.biblioteca.gestion_biblioteca.repository;

import com.biblioteca.gestion_biblioteca.model.AutorLibro;
import com.biblioteca.gestion_biblioteca.model.AutorLibroId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorLibroRepository extends JpaRepository<AutorLibro, AutorLibroId> {

}

