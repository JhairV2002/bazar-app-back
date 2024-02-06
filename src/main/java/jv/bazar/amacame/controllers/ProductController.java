package jv.bazar.amacame.controllers;


import jv.bazar.amacame.entity.Product;
import jv.bazar.amacame.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/list-all/")
    public ResponseEntity<List<Product>> listAll() {
        return productService.getAllProducts();
    }

    @GetMapping("/list-by-id/{productId}")
    public ResponseEntity<Product> listById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @PostMapping("/create/")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        product.setProductId(productId);
        return productService.updateProduct(product);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long productId) {
        return productService.deleteProductById(productId);
    }
}
