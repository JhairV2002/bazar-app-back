package jv.bazar.amacame.entity;

import jakarta.persistence.*;
import jv.bazar.amacame.cons.SchemaNamesConstants;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(schema = SchemaNamesConstants.BILLING_SCHEMA)
public class BillDetailLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billDetailLineId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    private BigDecimal totalPriceByProduct;
    private BigDecimal totalProfitByProduct;
    private Long quantity;
}
