package jv.bazar.amacame.dto.res;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BillResDTO {
    private Long billId;
    private Date billDate;
    private String billDescription;
    private BigDecimal billTotal;
    private BillDetailResDTO billDetail;
}
