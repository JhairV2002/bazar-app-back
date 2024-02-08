package jv.bazar.amacame.mappers;


import jv.bazar.amacame.dto.req.ProductReqDTO;
import jv.bazar.amacame.dto.res.ProductResDTO;
import jv.bazar.amacame.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductResDTO productToProductResDTO(Product product);

    List<ProductResDTO> productToProductResDTO(List<Product> product);

    Product productReqDTOToProduct(ProductReqDTO productReqDTO);

    ProductReqDTO productToProductReqDTO(Product product);

}
