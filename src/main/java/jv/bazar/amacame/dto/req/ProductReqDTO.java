package jv.bazar.amacame.dto.req;


import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductReqDTO implements Serializable {
        private  Long productId;
        private  String productName;
        private  Integer productStock;
        private BigDecimal productPurchasePrice;
        private  BigDecimal productSalePrice;
        private  BrandReqDto productBrand;
}
