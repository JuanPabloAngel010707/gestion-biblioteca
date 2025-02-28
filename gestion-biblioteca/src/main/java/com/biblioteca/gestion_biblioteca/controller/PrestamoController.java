// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.controller;

// Importa las clases necesarias para trabajar con los préstamos y los servicios asociados.
import com.biblioteca.gestion_biblioteca.model.Prestamo; // Importa la clase Prestamo.
import com.biblioteca.gestion_biblioteca.service.PrestamoService; // Importa el servicio de préstamos.
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Importa los códigos de estado HTTP.
import org.springframework.http.ResponseEntity; // Importa la clase para encapsular respuestas HTTP.
import org.springframework.web.bind.annotation.*; // Importa las anotaciones necesarias para trabajar con REST.

import java.util.List; // Importa las colecciones para manejar listas.
import java.util.Optional; // Importa Optional para manejar valores nulos.

@RestController // Indica que esta clase es un controlador de una API REST.
@RequestMapping("/api/prestamos") // Define la ruta base para las peticiones a este controlador.
public class PrestamoController {

    private final PrestamoService prestamoService; // Declara una dependencia del servicio PrestamoService.

    // Constructor con inyección de dependencias para inicializar el servicio de préstamos.
    @Autowired
    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    // Método para obtener todos los préstamos de la base de datos.
    @GetMapping // Mapea las peticiones GET a esta ruta.
    public ResponseEntity<List<Prestamo>> obtenerTodos() {
        // Llama al servicio para obtener la lista de préstamos.
        List<Prestamo> prestamos = prestamoService.buscarTodos();
        // Devuelve la lista de préstamos con un código de estado 200 (OK).
        return new ResponseEntity<>(prestamos, HttpStatus.OK);
    }

    // Método para obtener un préstamo específico por su ID.
    @GetMapping("/{id}") // Mapea las peticiones GET a la ruta "/{id}".
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        // Busca el préstamo en el servicio por su ID.
        Optional<Prestamo> prestamo = prestamoService.buscarPorId(id);
        // Si se encuentra el préstamo, lo devuelve con un código HTTP 200 (OK), si no lo encuentra, devuelve un código HTTP 404 (Not Found).
        if (prestamo.isPresent()) {
            return new ResponseEntity<>(prestamo.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Método para obtener los préstamos de un libro específico mediante su ISBN.
    @GetMapping("/libro/{isbnLibro}") // Mapea las peticiones GET a la ruta "/libro/{isbnLibro}".
    public ResponseEntity<?> buscarPorIsbnLibro(@PathVariable String isbnLibro) {
        // Busca los préstamos asociados al ISBN del libro.
        List<Prestamo> prestamos = prestamoService.buscarPorIsbnLibro(isbnLibro);
        // Si no se encuentran préstamos, devuelve un código HTTP 404 (Not Found).
        if (prestamos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // Si se encuentran préstamos, los devuelve con un código HTTP 200 (OK).
        return new ResponseEntity<>(prestamos, HttpStatus.OK);
    }

    // Método para obtener los préstamos de un usuario específico mediante su DNI.
    @GetMapping("/usuario/{dniUsuario}") // Mapea las peticiones GET a la ruta "/usuario/{dniUsuario}".
    public ResponseEntity<?> buscarPorDniUsuario(@PathVariable String dniUsuario) {
        // Busca los préstamos asociados al DNI del usuario.
        List<Prestamo> prestamos = prestamoService.buscarPorDniUsuario(dniUsuario);
        // Si no se encuentran préstamos, devuelve un código HTTP 404 (Not Found).
        if (prestamos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // Si se encuentran préstamos, los devuelve con un código HTTP 200 (OK).
        return new ResponseEntity<>(prestamos, HttpStatus.OK);
    }

    // Método para crear un nuevo préstamo.
    @PostMapping // Mapea las peticiones POST a esta ruta.
    public ResponseEntity<?> crearPrestamo(@RequestParam String isbnLibro, @RequestParam String dniUsuario) {
        try {
            // Llama al servicio para crear el préstamo con los parámetros proporcionados.
            Prestamo nuevoPrestamo = prestamoService.crearPrestamo(isbnLibro, dniUsuario);
            // Devuelve el préstamo creado con un código HTTP 201 (Created).
            return new ResponseEntity<>(nuevoPrestamo, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Si ocurre un error (por ejemplo, si el libro o el usuario no existen), devuelve un código 400 (Bad Request) con el mensaje de error.
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Método para devolver un préstamo.
    @PutMapping("/{id}/devolver") // Mapea las peticiones PUT a la ruta "/{id}/devolver".
    public ResponseEntity<?> devolverPrestamo(@PathVariable Long id) {
        try {
            // Llama al servicio para marcar el préstamo como devuelto.
            Prestamo prestamoDevuelto = prestamoService.devolverPrestamo(id);
            // Devuelve el préstamo devuelto con un código HTTP 200 (OK).
            return new ResponseEntity<>(prestamoDevuelto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Si ocurre un error (por ejemplo, si el préstamo no existe), devuelve un código 400 (Bad Request) con el mensaje de error.
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Método para eliminar un préstamo.
    @DeleteMapping("/{id}") // Mapea las peticiones DELETE a la ruta "/{id}".
    public ResponseEntity<?> eliminarPrestamo(@PathVariable Long id) {
        try {
            // Llama al servicio para eliminar el préstamo.
            prestamoService.eliminarPrestamo(id);
            // Si la eliminación es exitosa, devuelve un código HTTP 204 (No Content).
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            // Si ocurre un error (por ejemplo, si el préstamo no existe), devuelve un código 400 (Bad Request) con el mensaje de error.
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
