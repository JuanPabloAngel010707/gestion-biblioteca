package com.biblioteca.gestion_biblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "autor")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del autor no puede estar vacío.")
    @NotNull(message = "El nombre del autor no puede ser nulo.")
    @Size(min = 2, max = 100, message = "El nombre del autor debe tener entre 2 y 100 caracteres.")
    @Column
    private String nombre;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)  
    private Set<AutorLibro> autorLibros;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}

