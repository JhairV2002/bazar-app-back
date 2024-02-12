package jv.bazar.amacame.dto.req;

import jv.bazar.amacame.entity.BillDetailLine;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BillDetailReqDTO {
    private List<BillDetailLineReqDTO> billDetailLines;
}
