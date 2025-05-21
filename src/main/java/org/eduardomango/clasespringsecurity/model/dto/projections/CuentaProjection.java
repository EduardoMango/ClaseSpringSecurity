package org.eduardomango.clasespringsecurity.model.dto.projections;

import org.eduardomango.clasespringsecurity.model.enums.TipoCuenta;

public interface CuentaProjection {


    String getNumero();
    Double getSaldo();
    TipoCuenta getTipo();

}
