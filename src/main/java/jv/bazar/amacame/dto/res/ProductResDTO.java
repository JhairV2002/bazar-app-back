package jv.bazar.amacame.dto.res;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductResDTO {
    private final Long productId;
    private final String productName;
    private final Integer productStock;
    private final BigDecimal productPurchasePrice;
    private final BigDecimal productSalePrice;
    private final BigDecimal productProfit;
    private final BrandResDTO productBrand;
}
