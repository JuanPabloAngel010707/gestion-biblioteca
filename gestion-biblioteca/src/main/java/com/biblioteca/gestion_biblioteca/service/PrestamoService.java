package com.biblioteca.gestion_biblioteca.service;

import java.util.List;
import java.util.Optional;
import com.biblioteca.gestion_biblioteca.model.Prestamo;

public interface PrestamoService {
	List<Prestamo> buscarTodos();
    List<Prestamo> buscarPorIsbnLibro(String isbnLibro);
    List<Prestamo> buscarPorDniUsuario(String dniUsuario);
    Optional<Prestamo> buscarPorId(Long id);
    Prestamo crearPrestamo(String isbnLibro, String dniUsuario);
    void eliminarPrestamo(Long id);
    Boolean prestamoExistente(Long id);
}

