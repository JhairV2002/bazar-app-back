package jv.bazar.amacame.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromoResDTO {
    private Long promoId;
    private String promoName;
    private String promoDescription;
    private String promoType;
    private String promoScope;
    private BigDecimal promoValue;
    private Long specialPromoX;
    private Long specialPromoY;
}
