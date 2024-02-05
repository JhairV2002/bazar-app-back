package jv.bazar.amacame.services;

import jv.bazar.amacame.entity.Brand;
import jv.bazar.amacame.exceptions.CustomErrorException;
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

    /* List All Brands*/
    public ResponseEntity<List<Brand>>  getAllBrands() throws CustomErrorException {

            List<Brand> brands = brandRepository.findAll();
            return new ResponseEntity<>(brands, HttpStatus.OK);

    }
    /* List brand by id*/
    public ResponseEntity<Brand> getBrandById(Long brandId) throws CustomErrorException {
        try {
            Brand brand = brandRepository.findById(brandId).orElseThrow();
            return new ResponseEntity<>(brand, HttpStatus.OK);
        } catch (Exception e) {
            throw CustomErrorException.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("Marca no encontrada")
                    .data(null)
                    .build();
        }
    }

    public ResponseEntity<Brand> saveBrand(Brand brand) throws CustomErrorException {
        try {
            return new ResponseEntity<>(brandRepository.save(brand), HttpStatus.CREATED);
        } catch (Exception e) {
            throw CustomErrorException.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("No se pudo guardar la marca")
                    .data(e.getMessage())
                    .build();
        }
    }

    public ResponseEntity<Brand> updateBrand(Brand brand) throws CustomErrorException {
        try {
            Brand existingBrand = brandRepository.findById(brand.getBrandId()).orElseThrow();
            brand.setCreatedAt(existingBrand.getCreatedAt());
            return new ResponseEntity<>(brandRepository.save(brand), HttpStatus.OK);
        } catch (Exception e) {
            throw CustomErrorException.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("No se pudo actualizar la marca")
                    .data(null)
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
                    .data(null)
                    .build();
        }
    }
}

