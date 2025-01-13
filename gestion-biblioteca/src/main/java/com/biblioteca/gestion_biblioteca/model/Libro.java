package com.biblioteca.gestion_biblioteca.model;

import jakarta.persistence.*;
import java.util.Set;
import java.util.List;

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

    @Column(nullable = false)
    private Integer cantidad;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true)  
    private Set<AutorLibro> autorLibros;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL)
    private List<Prestamo> prestamos;

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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }
}
