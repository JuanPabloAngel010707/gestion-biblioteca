package com.biblioteca.gestion_biblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @Pattern(regexp = "^[0-9]{8}[A-Za-z]?$", message = "El DNI debe tener 8 dígitos y opcionalmente una letra.")
    @NotBlank(message = "El dni del usuario no puede estar vacío.")
    @NotNull(message = "El dni del usuario no puede ser nulo.")
    @Column(name = "dni")
    private String dni;
    
    @NotNull(message = "El nombre del usuario no puede ser nulo.")
    @NotBlank(message = "El nombre del usuario no puede estar vacío.")
    @Size(min = 2, message = "El nombre del usuario debe tener al menos 2 caracteres.")
    @Column
    private String nombre;

    @NotNull(message = "El apellido del usuario no puede ser nulo.")
    @NotBlank(message = "El apellido del usuario no puede estar vacío.")
    @Size(min = 2, message = "El apellido del usuario debe tener al menos 2 caracteres.")
    @Column
    private String apellido;

    @Pattern(regexp = "^\\+?[0-9\\s]+$", message = "El teléfono debe tener solo números y espacios (opcionalmente con '+' al inicio).")
    @NotNull(message = "El telefono del usuario no puede ser nulo.")
    @NotBlank(message = "El telefono del usuario no puede estar vacío.")
    @Column
    private String telefono;

    @NotNull(message = "La direccion del usuario no puede ser nula.")
    @NotBlank(message = "La direccion del usuario no puede estar vacía.")
    @Size(min = 2, message = "La direccion del usuario debe tener al menos 5 caracteres.")
    @Column
    private String direccion;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}

