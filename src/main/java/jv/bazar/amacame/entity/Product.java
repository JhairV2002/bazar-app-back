package jv.bazar.amacame.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

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
