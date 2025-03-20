package jv.bazar.amacame.entity;

import jakarta.persistence.*;
import jv.bazar.amacame.cons.SchemaNamesConstants;
import lombok.*;

import java.io.Serializable;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "permissions", schema = SchemaNamesConstants.AUTHENTICATION_SCHEMA)
public class Permissions implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, updatable = false)
    private String name;
}
