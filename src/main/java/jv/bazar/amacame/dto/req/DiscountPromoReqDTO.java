package jv.bazar.amacame.dto.req;

import jv.bazar.amacame.enums.PromoTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountPromoReqDTO {
    private Long promoId;
    private String promoName;
    private String promoDescription;
    private PromoTypeEnum promoType;
    private BigDecimal promoValue;
}
