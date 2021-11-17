package es.ivan.acceso.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class Alumno implements Serializable {

    private static final long serialVersionUID = 8799656487674716638L;

    private String curso;
    private String asignatura;
    private String nombre;
    private boolean aprobado;
    private float nota;
}
