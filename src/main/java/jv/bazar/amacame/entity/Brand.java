package jv.bazar.amacame.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
public class Brand {
    @Id
    private Long brandId;
    private String brandName;
}
