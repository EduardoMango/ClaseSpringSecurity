package org.eduardomango.clasespringsecurity.model.dto;

import lombok.Builder;
import lombok.Value;
import org.eduardomango.clasespringsecurity.model.entities.CuentaEntity;
import org.eduardomango.clasespringsecurity.model.enums.TipoCuenta;

import java.io.Serializable;

/**
 * DTO for {@link CuentaEntity}
 */
@Value
@Builder
public class CuentaDTO implements Serializable {

    String numero;
    Double saldo;
    TipoCuenta tipo;
}