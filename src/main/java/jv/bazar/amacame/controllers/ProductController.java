package jv.bazar.amacame.controllers;


import jv.bazar.amacame.dto.req.ProductReqDTO;
import jv.bazar.amacame.dto.res.ProductResDTO;
import jv.bazar.amacame.entity.Product;
import jv.bazar.amacame.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

//    @GetMapping("/list-all/")
//    public ResponseEntity<List<ProductResDTO>> listAll() {
//        return productService.getAllProducts();
//    }

    @GetMapping("/list-all/")
    public ResponseEntity<String> listAll() {
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }

    @GetMapping("/list-by-id/{productId}")
    public ResponseEntity<ProductResDTO> listById(@PathVariable Long productId) {
        return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
    }

    @PostMapping("/create/")
    public ResponseEntity<ProductResDTO> createProduct(@RequestBody ProductReqDTO product) {
        return productService.saveProduct(product);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductResDTO> updateProduct(@PathVariable Long productId, @RequestBody ProductReqDTO product) {
        return productService.updateProduct(product);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ProductResDTO> deleteProduct(@PathVariable Long productId) {
        return productService.deleteProductById(productId);
    }
}
