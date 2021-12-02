package es.ivan.acceso.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class Alumno implements Serializable, Comparable<Alumno> {

    private static final long serialVersionUID = 8687999564436676302L;

    private String nombre;
    private String asignatura;
    private String curso;
    private Boolean aprobado;
    private Float nota;

    @Override
    public int compareTo(Alumno o) {
        return this.nombre.toLowerCase().compareTo(o.getNombre().toLowerCase());
    }
}
