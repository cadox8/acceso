package es.ivan.acceso.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class Alumno implements Serializable {

    private static final long serialVersionUID = 8687999564436676302L;

    private String curso;
    private String asignatura;
    private String nombre;
    private Boolean aprobado;
    private Float nota;
}
