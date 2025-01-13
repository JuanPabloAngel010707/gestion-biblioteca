package com.biblioteca.gestion_biblioteca.service;

import java.util.List;
import java.util.Optional;
import com.biblioteca.gestion_biblioteca.model.Autor;

public interface AutorService {
	List<Autor> buscarTodos();
    List<Autor> buscarPorNombre(String nombre);
    Optional<Autor> buscarPorId(Long id);
    Autor crearAutor(Autor autor);
    void eliminarAutor(Long id);
    Autor crearAutorConLibro(String nombreAutor);
    Boolean autorExistente(Long id);
}

