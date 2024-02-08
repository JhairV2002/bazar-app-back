package jv.bazar.amacame.controllers;


import jv.bazar.amacame.dto.req.BrandReqDto;
import jv.bazar.amacame.dto.res.BrandProductResDTO;
import jv.bazar.amacame.dto.res.BrandResDTO;
import jv.bazar.amacame.entity.Brand;
import jv.bazar.amacame.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/brands")
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

    @PostMapping("/create/")
    public ResponseEntity<BrandResDTO> createBrand(@RequestBody BrandReqDto brand) {
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
