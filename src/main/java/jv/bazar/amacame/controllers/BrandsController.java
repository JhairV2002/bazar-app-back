package jv.bazar.amacame.controllers;


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
    public ResponseEntity<List<Brand>> listAll() {
        return brandService.getAllBrands();
    }

    @GetMapping("/list-by-id/{brandId}")
    public ResponseEntity<Brand> listById(@PathVariable Long brandId) {
        return brandService.getBrandById(brandId);
    }

    @PostMapping("/create/")
    public ResponseEntity<Brand> createBrand(@RequestBody Brand brand) {
        return brandService.saveBrand(brand);
    }

    @PutMapping("/update/{brandId}")
    public ResponseEntity<Brand> updateBrand(@PathVariable Long brandId, @RequestBody Brand brand) {
        brand.setBrandId(brandId);
        return brandService.updateBrand(brand);
    }
}
