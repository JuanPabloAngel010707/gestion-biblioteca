package com.biblioteca.gestion_biblioteca.service;

import com.biblioteca.gestion_biblioteca.model.AutorLibro;
import com.biblioteca.gestion_biblioteca.model.AutorLibroId;
import com.biblioteca.gestion_biblioteca.model.Libro;
import com.biblioteca.gestion_biblioteca.model.Autor;

public interface AutorLibroService {
    AutorLibro crearAutorLibro(Libro libro, Autor autor);
    void eliminarAutorLibro(AutorLibroId autorLibroId);
}

