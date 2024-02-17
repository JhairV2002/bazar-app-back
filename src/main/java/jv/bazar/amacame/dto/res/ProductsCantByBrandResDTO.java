package jv.bazar.amacame.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductsCantByBrandResDTO implements Serializable {
    private String brandName;
    private int cantProducts;
}
