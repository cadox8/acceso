package es.ivan.acceso.ems.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Data
@RequiredArgsConstructor
public class Intervention extends AbstractAPI {

    @LocalizeName(value = "ID")
    private final int id;

    @LocalizeName(value = "Médico")
    private final Medic medic;

    @LocalizeName(value = "Paciente")
    private final Patient patient;

    @LocalizeName(value = "Intervención")
    private final String problem;

    @LocalizeName(value = "Fecha y Hora")
    private final Instant date;
}
