package org.eduardomango.clasespringsecurity.repositories;

import org.eduardomango.clasespringsecurity.model.dto.projections.UserBasicInfoProjection;
import org.eduardomango.clasespringsecurity.model.dto.projections.UserProjection;
import org.eduardomango.clasespringsecurity.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    List<UserBasicInfoProjection> findAllProjectedBy();

    Optional<UserProjection> findProjectedByDni(String dni);
}
