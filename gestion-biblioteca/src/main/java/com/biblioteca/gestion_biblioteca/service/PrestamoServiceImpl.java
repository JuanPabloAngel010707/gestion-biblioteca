// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.service;

// Importa las clases necesarias para trabajar con préstamos, libros y usuarios.
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate; // Importa LocalDate para trabajar con fechas.

import com.biblioteca.gestion_biblioteca.model.Prestamo; // Importa la clase Prestamo para usarla en el servicio.
import com.biblioteca.gestion_biblioteca.model.Libro; // Importa la clase Libro.
import com.biblioteca.gestion_biblioteca.model.Usuario; // Importa la clase Usuario.
import com.biblioteca.gestion_biblioteca.repository.PrestamoRepository; // Importa el repositorio de Prestamo.

@Service // Indica que esta clase es un servicio gestionado por Spring.
public class PrestamoServiceImpl implements PrestamoService {

    // Inyección de dependencias para interactuar con los repositorios y otros servicios.
    private final PrestamoRepository prestamoRepository;
    private final LibroService libroService;
    private final UsuarioService usuarioService;

    // Constructor para inyectar las dependencias necesarias.
    @Autowired
    public PrestamoServiceImpl(PrestamoRepository prestamoRepository, LibroService libroService, UsuarioService usuarioService) {
        this.prestamoRepository = prestamoRepository;
        this.libroService = libroService;
        this.usuarioService = usuarioService;
    }

    // Método para obtener todos los préstamos registrados en la base de datos.
    @Override
    public List<Prestamo> buscarTodos() {
        return prestamoRepository.findAll();
    }

    // Método para obtener los préstamos asociados a un libro utilizando su ISBN.
    @Override
    public List<Prestamo> buscarPorIsbnLibro(String isbnLibro) {
        return prestamoRepository.findByLibroIsbn(isbnLibro);
    }

    // Método para obtener los préstamos realizados por un usuario, dado su DNI.
    @Override
    public List<Prestamo> buscarPorDniUsuario(String dniUsuario) {
        return prestamoRepository.findByUsuarioDni(dniUsuario);
    }

    // Método para obtener un préstamo específico por su ID.
    @Override
    public Optional<Prestamo> buscarPorId(Long id) {
        return prestamoRepository.findById(id);
    }

    // Método para crear un nuevo préstamo. Se verifica que el libro y el usuario existan y que el libro no esté ya prestado.
    @Override
    public Prestamo crearPrestamo(String isbnLibro, String dniUsuario) {
        // Verifica que el libro exista.
        Libro libro = libroService.buscarPorIsbn(isbnLibro).orElseThrow(() ->
                new IllegalArgumentException("El libro con ISBN " + isbnLibro + " no existe."));

        // Verifica que el usuario exista.
        Usuario usuario = usuarioService.buscarPorDni(dniUsuario).orElseThrow(() ->
                new IllegalArgumentException("El usuario con DNI " + dniUsuario + " no existe."));

        // Verifica si el libro ya está prestado.
        if (!prestamoRepository.findByLibroIsbnAndEstado(isbnLibro, "activo").isEmpty()) {
            throw new IllegalArgumentException("El libro con ISBN " + isbnLibro + " ya está prestado.");
        }

        // Verifica si el usuario ya tiene 5 préstamos activos.
        List<Prestamo> prestamosDelUsuario = prestamoRepository.findByUsuarioDniAndEstado(dniUsuario, "activo");
        if (prestamosDelUsuario.size() >= 5) {
            throw new IllegalArgumentException("El usuario con DNI " + dniUsuario + " ya tiene 5 préstamos activos.");
        }

        // Crea y guarda el nuevo préstamo.
        Prestamo prestamo = new Prestamo();
        prestamo.setLibro(libro);
        prestamo.setUsuario(usuario);
        prestamo.setFechaPrestamo(LocalDate.now()); // Establece la fecha actual como fecha de préstamo.
        prestamo.setEstado("activo"); // Marca el préstamo como activo.

        return prestamoRepository.save(prestamo); // Guarda el préstamo en el repositorio y lo devuelve.
    }

    // Método para eliminar un préstamo dado su ID.
    @Override
    public void eliminarPrestamo(Long id) {
        if (!prestamoExistente(id)) {
            throw new IllegalArgumentException("El préstamo con ID " + id + " no existe.");
        }
        prestamoRepository.deleteById(id); // Elimina el préstamo del repositorio.
    }

    // Método para devolver un préstamo. Cambia su estado a "devuelto".
    @Override
    public Prestamo devolverPrestamo(Long id) {
        // Obtiene el préstamo por ID. Lanza una excepción si no existe.
        Prestamo prestamo = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("El préstamo con ID " + id + " no existe."));

        // Verifica si el préstamo ya ha sido devuelto.
        if (!prestamo.getEstado().equals("activo")) {
            throw new IllegalArgumentException("El préstamo con ID " + id + " ya ha sido devuelto.");
        }

        // Marca el préstamo como devuelto y establece la fecha de devolución.
        prestamo.setFechaDevolucion(LocalDate.now());
        prestamo.setEstado("devuelto"); // Cambia el estado del préstamo a "devuelto".

        return prestamoRepository.save(prestamo); // Guarda los cambios en el repositorio y devuelve el préstamo actualizado.
    }

    // Método para verificar si un préstamo existe dado su ID.
    @Override
    public Boolean prestamoExistente(Long id) {
        Optional<Prestamo> prestamoExistente = buscarPorId(id);
        return prestamoExistente.isPresent(); // Devuelve true si el préstamo existe.
    }
}
