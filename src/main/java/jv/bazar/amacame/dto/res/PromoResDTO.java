package jv.bazar.amacame.dto.res;

import jv.bazar.amacame.enums.PromoTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromoResDTO {
    private Long promoId;
    private String promoName;
    private String promoDescription;
    private PromoTypeEnum promoType;
    private String promoScope;
    private String promoValue;
}
