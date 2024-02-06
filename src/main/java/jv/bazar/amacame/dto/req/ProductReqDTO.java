package jv.bazar.amacame.dto.req;


import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
public class ProductReqDTO implements Serializable {
        private final Long productId;
        private final String productName;
        private final Integer productStock;
        private final double productPurchasePrice;
        private final double productSalePrice;
        private final double productProfit;
        private final String productBrand;
}
