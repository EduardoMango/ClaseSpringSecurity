package org.eduardomango.clasespringsecurity.security.repositories;

import org.eduardomango.clasespringsecurity.security.entities.CredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialsRepository extends JpaRepository<CredentialsEntity, Long> {
    Optional<CredentialsEntity> findByEmail(String email);
}
