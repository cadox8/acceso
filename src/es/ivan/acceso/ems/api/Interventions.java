package es.ivan.acceso.ems.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Data
@RequiredArgsConstructor
public class Interventions extends AbstractAPI {

    @LocalizeName(value = "Médico")
    private final Medic medico;

    @LocalizeName(value = "Paciente")
    private final Patient paciente;

    @LocalizeName(value = "Intervención")
    private final String problem;

    @LocalizeName(value = "Fecha y Hora")
    private final Instant date;
}
