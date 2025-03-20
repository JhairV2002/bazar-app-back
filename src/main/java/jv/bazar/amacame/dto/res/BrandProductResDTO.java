package jv.bazar.amacame.dto.res;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BrandProductResDTO {
    private final Long brandId;
    private final String brandName;
    private final List<ProductResDTO> products;
}
