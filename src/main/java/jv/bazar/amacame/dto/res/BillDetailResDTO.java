package jv.bazar.amacame.dto.res;

import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jv.bazar.amacame.entity.BillDetailLine;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BillDetailResDTO {
    private List<BillDetailLineResDTO> billDetailLines;
}
