package org.eduardomango.clasespringsecurity.security.entities;

import jakarta.persistence.*;
import lombok.*;
import org.eduardomango.clasespringsecurity.security.enums.Permits;

@Entity
@Getter
@Setter
@Builder
@Table(name = "permits")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PermitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    Permits permit;
}

