package org.eduardomango.clasespringsecurity.security.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.eduardomango.clasespringsecurity.security.model.enums.Role;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Role role;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permits",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permit_id"))
    List<PermitEntity> permits;
}
