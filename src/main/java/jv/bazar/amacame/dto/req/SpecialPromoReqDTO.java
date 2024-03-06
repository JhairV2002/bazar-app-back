package jv.bazar.amacame.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialPromoReqDTO {
    private Long promoId;
    private String promoName;
    private String promoDescription;
    private String promoType;
    private String promoScope;
    private Long specialPromoX;
    private Long specialPromoY;
}
