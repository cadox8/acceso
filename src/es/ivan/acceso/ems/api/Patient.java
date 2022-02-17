package es.ivan.acceso.ems.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient extends AbstractAPI {

    @LocalizeName(value = "Id")
    private int id;

    @LocalizeName(value = "Nombre")
    private String name;

    @LocalizeName(value = "Teléfono")
    private String phone;

    @LocalizeName("Edad")
    private int age;

    @LocalizeName(value = "Altura")
    private double height;

    @LocalizeName(value = "Peso")
    private double weight;

    @LocalizeName(value = "Tipo de Sangre")
    private String blood;
}
