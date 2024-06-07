package jv.bazar.amacame.controllers;


import jv.bazar.amacame.dto.req.BrandReqDto;
import jv.bazar.amacame.dto.res.BrandProductResDTO;
import jv.bazar.amacame.dto.res.BrandResDTO;
import jv.bazar.amacame.dto.res.GenericResponseDTO;
import jv.bazar.amacame.dto.res.ProductsCantByBrandResDTO;
import jv.bazar.amacame.entity.Brand;
import jv.bazar.amacame.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
@CrossOrigin(origins = "http://localhost:3000/")
public class BrandsController {
    @Autowired
    private BrandService brandService;

    @GetMapping("/list-all/")
    public ResponseEntity<List<BrandResDTO>> listAll() {
        return brandService.getAllBrands();
    }

    @GetMapping("/list-by-id/{brandId}")
    public ResponseEntity<BrandResDTO> listById(@PathVariable Long brandId) {
        return brandService.getBrandById(brandId);
    }

    @GetMapping("/list-with-products/")
    public  ResponseEntity<List<BrandProductResDTO>> listWithProducts() {
        return brandService.getBrandWithProducts();
    }

    @GetMapping("/list-with-products-cant/")
    public ResponseEntity<List<ProductsCantByBrandResDTO>> listProductsCant() {
        return brandService.getProductsCantByBrand();
    }

    @PostMapping("/create/")
    public GenericResponseDTO<BrandResDTO> createBrand(@RequestBody BrandReqDto brand) {
        return brandService.saveBrand(brand);
    }

    @PutMapping("/update/{brandId}")
    public ResponseEntity<BrandResDTO> updateBrand(@PathVariable Long brandId, @RequestBody BrandReqDto brand) {
        return brandService.updateBrand(brandId, brand);
    }

    @DeleteMapping("/delete/{brandId}")
    public ResponseEntity<BrandResDTO> deleteBrand(@PathVariable Long brandId) {
        return brandService.deleteBrandById(brandId);
    }
}
