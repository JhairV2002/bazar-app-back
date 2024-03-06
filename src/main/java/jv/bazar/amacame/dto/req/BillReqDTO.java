package jv.bazar.amacame.dto.req;

import jv.bazar.amacame.enums.BillStatusEnum;
import jv.bazar.amacame.enums.PromoTypeEnum;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BillReqDTO {
    private Long billId;
    private Date billDate;
    private String billDescription;
    private BillDetailReqDTO billDetail;
    private Boolean isActive;
    private BigDecimal billTotal;
    private BigDecimal billProfit;
    private BillStatusEnum billStatus;
    private boolean hasProductPromo;
    private boolean hasBillPromo;
    private PromoTypeEnum promoType;
    private SpecialPromoReqDTO promo;
}
