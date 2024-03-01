package jv.bazar.amacame.entity;

import jakarta.persistence.*;
import jv.bazar.amacame.enums.PromoTypeEnum;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Data
@Entity
public class Promos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long promoId;
    private String promoName;
    private String promoDescription;
    @Enumerated(EnumType.STRING)
    private PromoTypeEnum promoType;
    private String promoScope;
    private String promoValue;
    private boolean isActive;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

}
