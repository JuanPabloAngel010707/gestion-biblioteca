package com.biblioteca.gestion_biblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.biblioteca.gestion_biblioteca.model.Prestamo;
import com.biblioteca.gestion_biblioteca.repository.PrestamoRepository;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository prestamoRepository;

    @Autowired
    public PrestamoServiceImpl(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
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
    public Prestamo crearPrestamo(Prestamo prestamo) {
        return prestamoRepository.save(prestamo);
    }

    @Override
    public void eliminarPrestamo(Long id) {
        prestamoRepository.deleteById(id);
    }
}

