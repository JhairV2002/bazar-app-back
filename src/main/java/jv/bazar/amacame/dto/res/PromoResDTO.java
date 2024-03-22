package jv.bazar.amacame.dto.res;

import jv.bazar.amacame.enums.PromoScopeEnum;
import jv.bazar.amacame.enums.PromoTypeEnum;
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
    private PromoTypeEnum promoType;
    private PromoScopeEnum promoScope;
    private BigDecimal promoValue;
    private Long specialPromoX;
    private Long specialPromoY;
}
