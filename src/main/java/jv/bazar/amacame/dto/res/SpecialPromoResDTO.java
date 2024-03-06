package jv.bazar.amacame.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialPromoResDTO {
    private Long promoId;
    private String promoName;
    private String promoDescription;
    private String promoType;
    private String promoScope;
    private Long specialPromoX;
    private Long specialPromoY;
}
