package jv.bazar.amacame.dto.req;

import jv.bazar.amacame.enums.PromoScopeEnum;
import jv.bazar.amacame.enums.PromoTypeEnum;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromoReqDTO {
    private Long promoId;
    private String promoName;
    private String promoDescription;
    private PromoTypeEnum promoType;
    private PromoScopeEnum promoScope;
    private Long specialPromoX;
    private Long specialPromoY;
    private BigDecimal promoValue;
}
