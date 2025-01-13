package com.biblioteca.gestion_biblioteca.model;

import jakarta.persistence.*;

@Entity
@Table(name = "autor_libro")  
public class AutorLibro {
	
	@EmbeddedId
    private AutorLibroId id; 

    @ManyToOne
    @MapsId("libroId")
    @JoinColumn(name = "isbn_libro", nullable = false)
    private Libro libro;

    @ManyToOne
    @MapsId("autorId")
    @JoinColumn(name = "id_autor", nullable = false)
    private Autor autor;
    
    public AutorLibroId getId() {
        return id;
    }

    public void setId(AutorLibroId id) {
        this.id = id;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }
}

