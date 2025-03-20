package jv.bazar.amacame.mappers;

import jv.bazar.amacame.dto.req.BrandReqDto;
import jv.bazar.amacame.dto.res.BrandProductResDTO;
import jv.bazar.amacame.dto.res.BrandResDTO;
import jv.bazar.amacame.dto.res.ProductResDTO;
import jv.bazar.amacame.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    BrandMapper INSTANCE = Mappers.getMapper(BrandMapper.class);

    @Mapping(source = "brandId", target = "brandId")
    BrandResDTO brandToBrandResDTO(Brand brand);

    @Mapping(source = "brandName", target = "brandName")
    Brand brandReqDTOToBrand(BrandReqDto brandReqDTO);

    @Mapping(source = "brandId", target = "brandId")
    List<BrandResDTO> brandToBrandResDTO(List<Brand> brand);

    @Mapping(target = "productBrand", ignore = true)
    List<BrandProductResDTO> brandToBrandProductResDTO(List<Brand> brand);

}
