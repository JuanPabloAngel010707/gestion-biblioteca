package com.biblioteca.gestion_biblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "libro")
public class Libro {

    @Id
    @Pattern(regexp = "^(?:978|979)\\d{10}$", message = "El isbn debe cumplir con el formato ISBN-13, pero no tener guiones.")
    @NotBlank(message = "El isbn del libro no puede estar vacío.")
    @NotNull(message = "El isbn del libro no puede ser nulo.")
    @Column(name = "isbn")
    private String isbn;

    @NotBlank(message = "El titulo del libro no puede estar vacío.")
    @NotNull(message = "El titulo del libro no puede ser nulo.")
    @Size(min = 2, max = 100, message = "El titulo del libro debe tener entre 2 y 100 caracteres.")
    @Column
    private String titulo;

    @NotBlank(message = "La editorial del libro no puede estar vacía.")
    @NotNull(message = "La editorial del libro no puede ser nula.")
    @Size(min = 2, max = 100, message = "La editorial del libro debe tener entre 2 y 100 caracteres.")
    @Column
    private String editorial;

    @NotBlank(message = "La edicion del libro no puede estar vacía.")
    @NotNull(message = "La edicion del libro no puede ser nula.")
    @Size(min = 2, max = 100, message = "La edicion del libro debe tener entre 2 y 100 caracteres.")
    @Column
    private String edicion;
    
    @NotNull(message = "El año de publicación del libro no puede ser nulo.")
    @Min(value = 1000, message = "El año de publicación debe ser un número de 4 dígitos.")
    @Max(value = 9999, message = "El año de publicación debe ser un número de 4 dígitos.")
    @Column(name = "año_pblccn")
    private Integer anoPublicacion;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true)  
    private Set<AutorLibro> autorLibros;

    // Getters y Setters
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getEdicion() {
        return edicion;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    public Integer getAnoPublicacion() {
        return anoPublicacion;
    }

    public void setAnoPublicacion(Integer anoPublicacion) {
        this.anoPublicacion = anoPublicacion;
    }

}
