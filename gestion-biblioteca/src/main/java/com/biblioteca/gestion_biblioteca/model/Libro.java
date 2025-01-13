package com.biblioteca.gestion_biblioteca.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "libro")
public class Libro {

    @Id
    @Column(name = "isbn")
    private String isbn;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String editorial;

    @Column(nullable = false)
    private String edicion;

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
