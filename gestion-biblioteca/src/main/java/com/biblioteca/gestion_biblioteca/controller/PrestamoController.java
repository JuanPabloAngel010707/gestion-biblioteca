package com.biblioteca.gestion_biblioteca.controller;

import com.biblioteca.gestion_biblioteca.model.Prestamo;
import com.biblioteca.gestion_biblioteca.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    @Autowired
    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @GetMapping
    public ResponseEntity<List<Prestamo>> obtenerTodos() {
        List<Prestamo> prestamos = prestamoService.buscarTodos();
        return new ResponseEntity<>(prestamos, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Prestamo> prestamo = prestamoService.buscarPorId(id);
        if (prestamo.isPresent()) {
            return new ResponseEntity<>(prestamo.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/libro/{isbnLibro}")
    public ResponseEntity<?> buscarPorIsbnLibro(@PathVariable String isbnLibro) {
        List<Prestamo> prestamos = prestamoService.buscarPorIsbnLibro(isbnLibro);
        if (prestamos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(prestamos, HttpStatus.OK);
    }
    
    @GetMapping("/usuario/{dniUsuario}")
    public ResponseEntity<?> buscarPorDniUsuario(@PathVariable String dniUsuario) {
        List<Prestamo> prestamos = prestamoService.buscarPorDniUsuario(dniUsuario);
        if (prestamos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(prestamos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> crearPrestamo(@RequestParam String isbnLibro, @RequestParam String dniUsuario) {
    	try {
        Prestamo nuevoPrestamo = prestamoService.crearPrestamo(isbnLibro, dniUsuario);
        return new ResponseEntity<>(nuevoPrestamo, HttpStatus.CREATED);
    	} catch (IllegalArgumentException e) {
        	return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPrestamo(@PathVariable Long id) {
    	try {
    		prestamoService.eliminarPrestamo(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    		
    	} catch (IllegalArgumentException e) {
        	return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
