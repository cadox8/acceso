package es.ivan.acceso.ems.api;

import lombok.Data;

import java.util.Date;

@Data
public class Patient extends AbstractAPI {

    @LocalizeName(value = "Id")
    private final int id;

    @LocalizeName(value = "Nombre")
    private final String name;

    @LocalizeName(value = "Teléfono")
    private final String phone;

    @LocalizeName("Año de nacimiento")
    private final Date dob;

    @LocalizeName(value = "Altura")
    private final double height;

    @LocalizeName(value = "Peso")
    private final double weight;

    @LocalizeName(value = "Tipo de Sangre")
    private final String blood;
}
