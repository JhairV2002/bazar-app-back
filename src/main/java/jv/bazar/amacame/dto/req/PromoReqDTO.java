package jv.bazar.amacame.dto.req;

import jv.bazar.amacame.enums.PromoScopeEnum;
import jv.bazar.amacame.enums.PromoTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromoReqDTO {
    private String promoName;
    private String promoDescription;
    private PromoTypeEnum promoType;
    private PromoScopeEnum promoScope;
    private String promoValue;
}
