package jv.bazar.amacame.dto.req;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandReqDto implements Serializable {
    private Long brandId;
    private String brandName;
}
