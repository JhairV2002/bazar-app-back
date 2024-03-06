package jv.bazar.amacame.dto.req;

import jv.bazar.amacame.dto.res.PromoResDTO;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BillDetailLineReqDTO {
    private Long quantity;
    private BigDecimal totalPriceByProduct;
    private BigDecimal totalProfitByProduct;
    private ProductReqDTO product;
    private Boolean hasPromo;
    private PromoResDTO promo;
}
