package org.eduardomango.clasespringsecurity.repositories;

import org.eduardomango.clasespringsecurity.model.dto.projections.CuentaProjection;
import org.eduardomango.clasespringsecurity.model.entities.CuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<CuentaEntity,Long> {

    Optional<CuentaProjection> findByNumero(String numero);
    List<CuentaProjection> findAllByUsuario_Dni(String usuarioDni);
}
