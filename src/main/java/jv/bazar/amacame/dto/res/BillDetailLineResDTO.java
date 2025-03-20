package jv.bazar.amacame.dto.res;

import jv.bazar.amacame.entity.Product;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BillDetailLineResDTO {
    private ProductResDTO product;
    private Long quantity;
    private BigDecimal totalPriceByProduct;
    private BigDecimal totalProfitByProduct;
    private BigDecimal price;
}
