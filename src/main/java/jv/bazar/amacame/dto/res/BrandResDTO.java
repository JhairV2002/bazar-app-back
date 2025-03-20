package jv.bazar.amacame.dto.res;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BrandResDTO {
    private final Long brandId;
    private final String brandName;
}
