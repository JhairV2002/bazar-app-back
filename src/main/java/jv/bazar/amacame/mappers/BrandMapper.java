package jv.bazar.amacame.mappers;

import jv.bazar.amacame.dto.req.BrandReqDto;
import jv.bazar.amacame.dto.res.BrandResDTO;
import jv.bazar.amacame.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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

}
