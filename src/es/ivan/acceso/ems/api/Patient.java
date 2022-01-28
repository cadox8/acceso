package es.ivan.acceso.ems.api;

import lombok.Data;

@Data
public class Patient extends AbstractAPI {

    private final int id;

    private final String name;
    private final int phone;
    private final int age;
    private final double height;
    private final double weight;
    private final String blood;
}
