package jv.bazar.amacame.dto.req;

import jv.bazar.amacame.entity.BillDetail;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

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
}
