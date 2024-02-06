package jv.bazar.amacame.dto.res;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResDTO {
    private final Long productId;
    private final String productName;
    private final Integer productStock;
    private final double productPurchasePrice;
    private final double productSalePrice;
    private final double productProfit;
    private final String productBrand;
}
