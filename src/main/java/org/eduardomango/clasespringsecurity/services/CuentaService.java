package org.eduardomango.clasespringsecurity.services;

import org.eduardomango.clasespringsecurity.model.dto.CuentaDTO;
import org.eduardomango.clasespringsecurity.model.entities.CuentaEntity;
import org.eduardomango.clasespringsecurity.repositories.CuentaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;

    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    public CuentaDTO save(CuentaEntity entity) {
        CuentaEntity saved =  cuentaRepository.save(entity);

        return CuentaDTO.builder()
                .tipo(saved.getTipo())
                .saldo(saved.getSaldo())
                .numero(saved.getNumero())
                .build();
    }
    public void delete(Long id) {
        cuentaRepository.deleteById(id);
    }
    public CuentaDTO findByNumero(String numero) {
        return cuentaRepository.findByNumero(numero)
                .map(c -> CuentaDTO.builder()
                        .tipo(c.getTipo())
                        .saldo(c.getSaldo())
                        .numero(c.getNumero())
                        .build())
                .orElseThrow(NoSuchElementException::new);
    }
    public CuentaDTO update(CuentaEntity entity) {
        if(cuentaRepository.existsById(entity.getId())){
            return save(entity);
        }
        throw new NoSuchElementException();
    }
    public List<CuentaDTO> findAllByUserId(String dni) {
        return cuentaRepository.findAllByUsuario_Dni(dni)
                .stream()
                .map(c -> CuentaDTO.builder()
                        .tipo(c.getTipo())
                        .saldo(c.getSaldo())
                        .numero(c.getNumero())
                        .build())
                .toList();
    }

    public List<CuentaDTO> findAll() {
        return cuentaRepository.findAll()
                .stream()
                .map(c -> CuentaDTO.builder()
                        .tipo(c.getTipo())
                        .saldo(c.getSaldo())
                        .numero(c.getNumero())
                        .build())
                .toList();
    }

}
