package org.eduardomango.clasespringsecurity.model.mappers.impl;

import org.eduardomango.clasespringsecurity.model.dto.CuentaDTO;
import org.eduardomango.clasespringsecurity.model.entities.CuentaEntity;
import org.eduardomango.clasespringsecurity.model.mappers.interfaces.IMapper;

public class CuentaMapper implements IMapper<CuentaEntity, CuentaDTO> {
    @Override
    public CuentaDTO mapTo(CuentaEntity cuentaEntity) {
        return CuentaDTO.builder()
                .tipo(cuentaEntity.getTipo())
                .saldo(cuentaEntity.getSaldo())
                .numero(cuentaEntity.getNumero())
                .build();
    }

    @Override
    public CuentaEntity mapFrom(CuentaDTO cuentaDTO) {
        return CuentaEntity.builder()
                .tipo(cuentaDTO.getTipo())
                .saldo(cuentaDTO.getSaldo())
                .numero(cuentaDTO.getNumero())
                .build();
    }
}
