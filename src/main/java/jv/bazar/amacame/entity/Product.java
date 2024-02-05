package jv.bazar.amacame.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
    private Double productBuyPrice;
    private Double productSellPrice;
    private boolean isActive;
    @CreatedDate
    private String createdAt;
    @LastModifiedDate
    private String updatedAt;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "brandId")
    private Brand productBrand;
}
