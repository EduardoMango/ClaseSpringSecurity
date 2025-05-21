package org.eduardomango.clasespringsecurity.model.dto.projections;

import org.eduardomango.clasespringsecurity.model.enums.EstadoCivil;

public interface UserProjection {

    String getDni();
    String getNombre();
    String getApellido();
    String getEmail();
    String getCuit();
    int getEdad();
    EstadoCivil getEstadoCivil();
}
