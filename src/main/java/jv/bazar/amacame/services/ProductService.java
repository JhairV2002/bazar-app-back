package jv.bazar.amacame.services;

import jv.bazar.amacame.dto.req.ProductReqDTO;
import jv.bazar.amacame.dto.res.BrandResDTO;
import jv.bazar.amacame.dto.res.ProductResDTO;
import jv.bazar.amacame.entity.Brand;
import jv.bazar.amacame.entity.Product;
import jv.bazar.amacame.exceptions.CustomErrorException;
import jv.bazar.amacame.mappers.BrandMapper;
import jv.bazar.amacame.mappers.ProductMapper;
import jv.bazar.amacame.repositories.BrandRepository;
import jv.bazar.amacame.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private BrandMapper brandMapper;


    /* List All Products*/
    public ResponseEntity<List<ProductResDTO>> getAllProducts() throws CustomErrorException {
        try {
            List<Product> products = productRepository.findByIsActiveAndProductBrand_isActive(true, true);
            List<ProductResDTO> productResDTOS = productMapper.productToProductResDTO(products);

            return new ResponseEntity<>(productResDTOS, HttpStatus.OK);
        } catch (Exception e) {
            throw CustomErrorException.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("No se encontraron productos")
                    .data(e.getMessage())
                    .build();
        }
    }

    /* List product by id*/
    public ResponseEntity<ProductResDTO> getProductById(Long productId) throws CustomErrorException {
        try {
            Product product = productRepository.findByProductIdAndIsActive(productId, true);
            if (product == null) {
                throw CustomErrorException.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("Producto no encontrado")
                        .data(product)
                        .build();
            }
            ProductResDTO productResDTO = productMapper.productToProductResDTO(product);
            return new ResponseEntity<>(productResDTO, HttpStatus.OK);
        } catch (Exception e) {
            throw CustomErrorException.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("Producto no encontrado")
                    .data(e.getMessage())
                    .build();
        }
    }

    /* Save product*/

    public ResponseEntity<ProductResDTO> saveProduct(ProductReqDTO product) throws CustomErrorException {
        try {
            /* Validate brand */
            if (product.getProductBrand() == null) {
                throw CustomErrorException.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message("La marca no puede ser nula")
                        .build();
            }
            Brand brand = brandRepository.findByBrandIdAndIsActive(product.getProductBrand().getBrandId(), true);

            if (brand == null) {
                throw CustomErrorException.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message("La marca no existe o está eliminada")
                        .build();
            }

            BigDecimal productProfit = calculateProductProfit(product.getProductSalePrice(), product.getProductPurchasePrice());

            Product productToSave = productMapper.productReqDTOToProduct(product);

            productToSave.setActive(true);

            productToSave.setProductProfit(productProfit);

            Product savedProduct = productRepository.save(productToSave);
            return new ResponseEntity<>(productMapper.productToProductResDTO(savedProduct), HttpStatus.CREATED);

        } catch (Exception e) {
            throw CustomErrorException.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .data(e.getStackTrace().toString())
                    .build();
        }
    }

    /* Update product*/
    public ResponseEntity<ProductResDTO> updateProduct(ProductReqDTO product) throws CustomErrorException {
        try {
            BigDecimal productProfit = calculateProductProfit(product.getProductSalePrice(), product.getProductPurchasePrice());
            Product existingProduct = productRepository.findById(product.getProductId()).orElseThrow();
            existingProduct.setProductName(product.getProductName());
            existingProduct.setProductStock(product.getProductStock());
            existingProduct.setProductPurchasePrice(product.getProductPurchasePrice());
            existingProduct.setProductSalePrice(product.getProductSalePrice());
            existingProduct.setProductProfit(productProfit);
            existingProduct.setProductBrand(brandMapper.brandReqDTOToBrand(product.getProductBrand()));
            Product savedProduct = productRepository.save(existingProduct);
            return new ResponseEntity<>(productMapper.productToProductResDTO(savedProduct), HttpStatus.OK);
        } catch (Exception e) {
            throw CustomErrorException.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("No se pudo actualizar el producto")
                    .data(e.getMessage())
                    .build();
        }
    }

    /* Delete product by id*/
    public ResponseEntity<ProductResDTO> deleteProductById(Long productId) throws CustomErrorException {
        try {
            Product product = productRepository.findById(productId).orElseThrow();
            product.setActive(false);
            Product savedProduct = productRepository.save(product);
            return new ResponseEntity<>(productMapper.productToProductResDTO(savedProduct), HttpStatus.OK);
        } catch (Exception e) {
            throw CustomErrorException.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("Producto no encontrado")
                    .data(e.getMessage())
                    .build();
        }
    }

    public BigDecimal calculateProductProfit(BigDecimal productSalePrice, BigDecimal productPurchasePrice) {
        /* Validate Sale Price */
        if ((productSalePrice.compareTo(productPurchasePrice) < 0))
        {
            throw CustomErrorException.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("El precio de venta no puede ser menor que el precio de compra o igual a 0")
                    .build();
        }
        /* Validate product purchase price */
        if (productPurchasePrice.compareTo(BigDecimal.ZERO) <= 0){
            throw CustomErrorException.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("El precio de compra no puede ser menor o igual a 0")
                    .build();
        }
        /* Calculate product profit */
        BigDecimal productProfit = productSalePrice.subtract(productPurchasePrice);

        return productProfit;
    }
}
