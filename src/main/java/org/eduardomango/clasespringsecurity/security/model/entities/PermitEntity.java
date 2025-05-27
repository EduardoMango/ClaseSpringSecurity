package org.eduardomango.clasespringsecurity.security.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.eduardomango.clasespringsecurity.security.model.enums.Permit;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class PermitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    Permit permit;


}
