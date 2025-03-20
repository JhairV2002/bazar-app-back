package jv.bazar.amacame.entity;

import jakarta.persistence.*;
import jv.bazar.amacame.cons.SchemaNamesConstants;
import jv.bazar.amacame.enums.PromoTypeEnum;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Entity
@Table(schema = SchemaNamesConstants.PROMOTION_SCHEMA)
public class Promos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long promoId;
    private String promoName;
    private String promoDescription;
    @Enumerated(EnumType.STRING)
    private PromoTypeEnum promoType;
    private BigDecimal promoValue;
    private boolean isActive = true;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

}
