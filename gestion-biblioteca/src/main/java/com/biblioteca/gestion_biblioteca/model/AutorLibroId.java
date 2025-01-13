package com.biblioteca.gestion_biblioteca.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AutorLibroId implements Serializable{

    private String libroId; 
    private Long autorId;   

    public AutorLibroId() {}

    public AutorLibroId(String libroId, Long autorId) {
        this.libroId = libroId;
        this.autorId = autorId;
    }

    public String getLibroId() {
        return libroId;
    }

    public void setLibroId(String libroId) {
        this.libroId = libroId;
    }

    public Long getAutorId() {
        return autorId;
    }

    public void setAutorId(Long autorId) {
        this.autorId = autorId;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AutorLibroId that = (AutorLibroId) o;
        return Objects.equals(libroId, that.libroId) && Objects.equals(autorId, that.autorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(libroId, autorId);
    }

}

