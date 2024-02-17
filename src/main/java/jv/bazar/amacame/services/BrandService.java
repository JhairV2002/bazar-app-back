package jv.bazar.amacame.services;

import jv.bazar.amacame.dto.req.BrandReqDto;
import jv.bazar.amacame.dto.res.BrandProductResDTO;
import jv.bazar.amacame.dto.res.BrandResDTO;
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

    public ResponseEntity<List<BrandProductResDTO>> getBrandWithProducts() throws CustomErrorException {
        try {
            List<Brand> brands = brandRepository.findByIsActive(true);
            List<BrandProductResDTO> brandProductResDTOS = brandMapper.brandToBrandProductResDTO(brands);
            return new ResponseEntity<>(brandProductResDTOS, HttpStatus.OK);
        } catch (CustomErrorException e) {
            throw CustomErrorException.builder()
                    .status(e.getStatus())
                    .message(e.getMessage())
                    .data(e.getStackTrace())
                    .build();
        }
    }

    public ResponseEntity<List<ProductsCantByBrandResDTO>> getProductsCantByBrand() throws CustomErrorException {
        try{

        List<ProductsCantByBrandResDTO> productsCantByBrandResDTOS = new ArrayList<>();
        List<Brand> brands = brandRepository.findByIsActive(true);
        List<BrandProductResDTO> brandProductResDTOS = brandMapper.brandToBrandProductResDTO(brands);
        for (BrandProductResDTO brandProductResDTO : brandProductResDTOS) {
            productsCantByBrandResDTOS.add(ProductsCantByBrandResDTO.builder()
                    .brandName(brandProductResDTO.getBrandName())
                    .cantProducts(brandProductResDTO.getProducts().size())
                    .build());
        }
        return new ResponseEntity<>(productsCantByBrandResDTOS, HttpStatus.OK);
        } catch (CustomErrorException e) {
            throw CustomErrorException.builder()
                    .status(e.getStatus())
                    .message(e.getMessage())
                    .data(e.getStackTrace())
                    .build();
        }
    }

    public ResponseEntity<BrandResDTO> saveBrand(BrandReqDto brand) throws CustomErrorException {
        try {
            Brand existingBrand = brandRepository.findByBrandNameAndIsActive(brand.getBrandName(), true);
            Brand brandToSave = brandMapper.brandReqDTOToBrand(brand);
            brandToSave.setActive(true);
            Brand savedBrand = brandRepository.save(brandToSave);

            if (existingBrand != null) {
                throw CustomErrorException.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message("La marca ya existe")
                        .data(existingBrand)
                        .build();
            }

            return new ResponseEntity<>(brandMapper.brandToBrandResDTO(savedBrand), HttpStatus.CREATED);
        } catch (CustomErrorException e) {
            throw CustomErrorException.builder()
                    .status(e.getStatus())
                    .message(e.getMessage())
                    .data(e.getStackTrace())
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

