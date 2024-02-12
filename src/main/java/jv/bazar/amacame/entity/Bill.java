package jv.bazar.amacame.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.util.List;

@Entity
@Data
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;
    private Date billDate;
    private String billDescription;
    private BigDecimal billTotal;
    private BigDecimal billProfit;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private BillDetail billDetail;
    private BigDecimal billDetailTotal;
    private Boolean isActive;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;
}
