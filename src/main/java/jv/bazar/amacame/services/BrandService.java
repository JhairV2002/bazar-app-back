package jv.bazar.amacame.services;

import jv.bazar.amacame.dto.req.BrandReqDto;
import jv.bazar.amacame.dto.res.BrandProductResDTO;
import jv.bazar.amacame.dto.res.BrandResDTO;
import jv.bazar.amacame.dto.res.GenericResponseDTO;
import jv.bazar.amacame.dto.res.ProductsCantByBrandResDTO;
import jv.bazar.amacame.entity.Brand;
import jv.bazar.amacame.exceptions.CustomErrorException;
import jv.bazar.amacame.mappers.BrandMapper;
import jv.bazar.amacame.repositories.BrandRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandMapper brandMapper;

    /* List All Brands*/
    public ResponseEntity<List<BrandResDTO>> getAllBrands() throws CustomErrorException {
        try {
            List<Brand> brands = brandRepository.findByIsActive(true);
            List<BrandResDTO> brandResDTOS = brandMapper.brandToBrandResDTO(brands);
            return new ResponseEntity<>(brandResDTOS, HttpStatus.OK);
        } catch (Exception e) {
            throw CustomErrorException.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("No se encontraron marcas")
                    .data(e.getMessage())
                    .build();
        }
    }

    /* List brand by id*/
    public ResponseEntity<BrandResDTO> getBrandById(Long brandId) throws CustomErrorException {
        try {
            Brand brand = brandRepository.findByBrandIdAndIsActive(brandId, true);
            if (brand == null){
                throw CustomErrorException.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("Marca no encontrada")
                        .data("Marca no encontrada")
                        .build();
            }
            return new ResponseEntity<>(brandMapper.brandToBrandResDTO(brand), HttpStatus.OK);
        } catch (CustomErrorException e) {
            throw CustomErrorException.builder()
                    .status(e.getStatus())
                    .message(e.getMessage())
                    .data(e.getStackTrace())
                    .build();
        }
    }

    /*List Brand with Products */

    public GenericResponseDTO<List<BrandProductResDTO>> getBrandWithProducts() throws CustomErrorException {
        try {
            List<Brand> brands = brandRepository.findByIsActive(true);
            List<BrandProductResDTO> brandProductResDTOS = brandMapper.brandToBrandProductResDTO(brands);
            return  GenericResponseDTO.<List<BrandProductResDTO>>builder()
                    .code(HttpStatus.OK.value())
                    .message("Transacción exitosa")
                    .data(brandProductResDTOS)
                    .status(HttpStatus.OK)
                    .build();
        } catch (CustomErrorException e) {
            return  GenericResponseDTO.<List<BrandProductResDTO>>builder()
                    .status(e.getStatus())
                    .code(e.getStatus().value())
                    .message(e.getMessage())
                    .data(null)
                    .build();
        }
    }

    public GenericResponseDTO<List<ProductsCantByBrandResDTO>> getProductsCantByBrand() throws CustomErrorException {
        try{

        List<ProductsCantByBrandResDTO> productsCantByBrandResDTOS = new ArrayList<>();
        List<Brand> brands = brandRepository.findByIsActive(true);
        List<BrandProductResDTO> brandProductResDTOS = brandMapper.brandToBrandProductResDTO(brands);
        for (BrandProductResDTO brandProductResDTO : brandProductResDTOS) {
            productsCantByBrandResDTOS.add(ProductsCantByBrandResDTO.builder()
                    .brandId(brandProductResDTO.getBrandId())
                    .brandName(brandProductResDTO.getBrandName())
                    .cantProducts(brandProductResDTO.getProducts().size())
                    .build());
        }
        return GenericResponseDTO.<List<ProductsCantByBrandResDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Transacción exitosa")
                .data(productsCantByBrandResDTOS)
                .status(HttpStatus.OK)
                .build();
        } catch (CustomErrorException e) {
            return GenericResponseDTO.<List<ProductsCantByBrandResDTO>>builder()
                    .status(e.getStatus())
                    .code(e.getStatus().value())
                    .message(e.getMessage())
                    .data(null)
                    .build();
        }
    }

    public GenericResponseDTO<BrandResDTO> saveBrand(BrandReqDto brand) throws CustomErrorException {
        try {
            Brand existingBrand = brandRepository.findByBrandNameAndIsActive(brand.getBrandName(), true);
            if (existingBrand != null) {
                throw CustomErrorException.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message("La marca ya existe")
                        .data(existingBrand)
                        .build();
            }

            if(brand.getBrandName().isEmpty()){
                throw CustomErrorException.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message("El nombre de la marca no puede estar vacío")
                        .build();
            }
            Brand brandToSave = brandMapper.brandReqDTOToBrand(brand);
            brandToSave.setActive(true);
            Brand savedBrand = brandRepository.save(brandToSave);

            return GenericResponseDTO.<BrandResDTO>builder()
                    .code(HttpStatus.CREATED.value())
                    .message("Marca creada exitosamente")
                    .data(brandMapper.brandToBrandResDTO(savedBrand))
                    .status(HttpStatus.CREATED)
                    .build();

        } catch (CustomErrorException e) {
            return GenericResponseDTO.<BrandResDTO>builder()
                    .status(e.getStatus())
                    .code(e.getStatus().value())
                    .message(e.getMessage())
                    .data(null)
                    .build();
        }
    }

    public ResponseEntity<BrandResDTO> updateBrand(Long brandId, BrandReqDto brand) throws CustomErrorException {
        try {
            Brand brandtoUpdate = brandRepository.findByBrandIdAndIsActive(brandId, true);
            if (brandtoUpdate == null) {
                throw CustomErrorException.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("Marca ya eliminada o no encontrada")
                        .data(brandtoUpdate)
                        .build();
            }
            if (brand.getBrandName() == null ) {
                throw CustomErrorException.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message("El nombre de la marca no puede estar vacío")
                        .build();
            }
                brandtoUpdate.setBrandName(brand.getBrandName());
                return new ResponseEntity<>(brandMapper.brandToBrandResDTO(brandRepository.save(brandtoUpdate)), HttpStatus.OK);

        } catch (CustomErrorException e) {
            throw CustomErrorException.builder()
                    .status(e.getStatus())
                    .message(e.getMessage())
                    .data(e.getStackTrace())
                    .build();
        }
    }

    public ResponseEntity<BrandResDTO> deleteBrandById(Long brandId) throws CustomErrorException {
        try {
            Brand brand = brandRepository.findByBrandIdAndIsActive(brandId, true);
            if (brand == null) {
                throw CustomErrorException.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("Brand not found")
                        .data(brand)
                        .build();
            }
            brand.setActive(false);
            BrandResDTO brandResDTO = brandMapper.brandToBrandResDTO(brandRepository.save(brand));
            return new ResponseEntity<>(brandResDTO, HttpStatus.OK);
        } catch (CustomErrorException e) {
            throw CustomErrorException.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("Brand not found")
                    .data(e.getMessage())
                    .build();
        }
    }
}

