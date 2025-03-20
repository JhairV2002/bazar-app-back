package jv.bazar.amacame.entity;

import jakarta.persistence.*;
import jv.bazar.amacame.cons.SchemaNamesConstants;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(schema = SchemaNamesConstants.AUTHENTICATION_SCHEMA)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String token;
    private String username;
    private Timestamp creationDate;
    private Timestamp expirationDate;
    private boolean isActive;
}
