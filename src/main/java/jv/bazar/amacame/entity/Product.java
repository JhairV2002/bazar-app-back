package jv.bazar.amacame.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Entity
public class Product {
    @Id
    private Long productId;
    private String productName;
    private Integer productStock;
    private Double productBuyPrice;
    private Double productSellPrice;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "brandId")
    private Brand productBrand;
}
