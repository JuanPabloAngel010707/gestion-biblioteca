// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.service;

// Importa las clases necesarias para manejar préstamos.
import java.util.List;
import java.util.Optional;
import com.biblioteca.gestion_biblioteca.model.Prestamo; // Importa la clase Prestamo para trabajar con este tipo de objeto.

// Interfaz que define las operaciones que se pueden realizar sobre los préstamos.
// Estas operaciones serán implementadas por una clase de servicio.
public interface PrestamoService {

    // Método para obtener todos los préstamos registrados en la base de datos.
    List<Prestamo> buscarTodos();

    // Método para buscar los préstamos asociados a un libro mediante su ISBN.
    List<Prestamo> buscarPorIsbnLibro(String isbnLibro);

    // Método para buscar los préstamos realizados por un usuario, dado su DNI.
    List<Prestamo> buscarPorDniUsuario(String dniUsuario);

    // Método para buscar un préstamo específico mediante su ID. Retorna un Optional para manejar el caso de que el préstamo no exista.
    Optional<Prestamo> buscarPorId(Long id);

    // Método para crear un nuevo préstamo. Recibe el ISBN del libro y el DNI del usuario que realiza el préstamo.
    Prestamo crearPrestamo(String isbnLibro, String dniUsuario);

    // Método para eliminar un préstamo. Recibe el ID del préstamo a eliminar.
    void eliminarPrestamo(Long id);

    // Método para verificar si un préstamo existe en la base de datos. Devuelve un valor booleano.
    Boolean prestamoExistente(Long id);

    // Método para marcar un préstamo como devuelto. Recibe el ID del préstamo.
    Prestamo devolverPrestamo(Long id);
}
