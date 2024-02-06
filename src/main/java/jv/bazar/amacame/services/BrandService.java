package jv.bazar.amacame.services;

import jv.bazar.amacame.dto.req.BrandReqDto;
import jv.bazar.amacame.dto.res.BrandResDTO;
import jv.bazar.amacame.entity.Brand;
import jv.bazar.amacame.exceptions.CustomErrorException;
import jv.bazar.amacame.mappers.BrandMapper;
import jv.bazar.amacame.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
            List<Brand> brands = brandRepository.findAll();
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
            Brand brand = brandRepository.findById(brandId).orElseThrow();
            return new ResponseEntity<>(brandMapper.brandToBrandResDTO(brand), HttpStatus.OK);
        } catch (Exception e) {
            throw CustomErrorException.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("Marca no encontrada")
                    .data(e.getMessage())
                    .build();
        }
    }

    public ResponseEntity<BrandResDTO> saveBrand(BrandReqDto brand) throws CustomErrorException {
        try {
            Brand existingBrand = brandRepository.findByBrandNameAndIsActive(brand.getBrandName(), true);
            Brand brandToSave = brandMapper.brandReqDTOToBrand(brand);
            Brand savedBrand = brandRepository.save(brandToSave);

            if (existingBrand != null) {
                throw CustomErrorException.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message("La marca ya existe")
                        .data(existingBrand)
                        .build();
            }

            return new ResponseEntity<>(brandMapper.brandToBrandResDTO(savedBrand), HttpStatus.CREATED);
        } catch (Exception e) {
            throw CustomErrorException.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("No se pudo guardar la marca")
                    .data(e.getMessage())
                    .build();
        }
    }

    public ResponseEntity<BrandResDTO> updateBrand(Long brandId, BrandReqDto brand) throws CustomErrorException {
        try {
            Brand brandtoUpdate = brandRepository.findById(brandId).orElseThrow();
            if (brand.getBrandName() == null ) {
                throw CustomErrorException.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message("El nombre de la marca no puede estar vacío")
                        .build();
            }
                brandtoUpdate.setBrandName(brand.getBrandName());
                return new ResponseEntity<>(brandMapper.brandToBrandResDTO(brandRepository.save(brandtoUpdate)), HttpStatus.OK);

        } catch (Exception e) {
            throw CustomErrorException.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("No se pudo actualizar la marca")
                    .data(e.getMessage())
                    .build();
        }
    }

    public ResponseEntity<Brand> deleteBrandById(Long brandId) throws CustomErrorException {
        try {
            Brand brand = brandRepository.findById(brandId).orElseThrow();
            brand.setActive(false);
            return new ResponseEntity<>(brandRepository.save(brand), HttpStatus.OK);
        } catch (Exception e) {
            throw CustomErrorException.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("Brand not found")
                    .data(e.getMessage())
                    .build();
        }
    }
}

