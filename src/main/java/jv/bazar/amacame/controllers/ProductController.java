package jv.bazar.amacame.controllers;


import jv.bazar.amacame.dto.req.ProductReqDTO;
import jv.bazar.amacame.dto.res.GenericResponseDTO;
import jv.bazar.amacame.dto.res.ProductResDTO;
import jv.bazar.amacame.exceptions.CustomErrorException;
import jv.bazar.amacame.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/list-all/")
    public GenericResponseDTO<List<ProductResDTO>> listAll() {
        return productService.getAllProducts();
    }

    @GetMapping("/list-by-id/{productId}")
    public GenericResponseDTO<ProductResDTO> listById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @PostMapping("/create/")
    public GenericResponseDTO<ProductResDTO> createProduct(@RequestBody ProductReqDTO product) {
        try {
            return productService.saveProduct(product);

        } catch (CustomErrorException e) {
            return GenericResponseDTO.<ProductResDTO>builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .data(null)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .build();
        }
    }

    @PutMapping("/update/{productId}")
    public GenericResponseDTO<ProductResDTO> updateProduct(@PathVariable Long productId, @RequestBody ProductReqDTO product) {
        return productService.updateProduct(product);
    }

    @DeleteMapping("/delete/{productId}")
    public GenericResponseDTO<ProductResDTO> deleteProduct(@PathVariable Long productId) {
        return productService.deleteProductById(productId);
    }
}
