package jv.bazar.amacame.entity;


import jakarta.persistence.*;
import jv.bazar.amacame.cons.SchemaNamesConstants;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = SchemaNamesConstants.PRODUCT_SCHEMA)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private Integer productStock;
    private BigDecimal productPurchasePrice;
    private BigDecimal productSalePrice;
    private BigDecimal productProfit;
    private boolean isActive = true;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;
    @ManyToOne()
    @JoinColumn(name = "brandId")
    private Brand productBrand;

    public boolean getIsActive(){
        return this.isActive;
    }
}
