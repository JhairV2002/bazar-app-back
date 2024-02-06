package jv.bazar.amacame.services;

import jv.bazar.amacame.entity.Product;
import jv.bazar.amacame.exceptions.CustomErrorException;
import jv.bazar.amacame.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    /* List All Products*/
    public ResponseEntity<List<Product>> getAllProducts() throws CustomErrorException {
        try {
            List<Product> products = productRepository.findAll();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            throw CustomErrorException.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("No se encontraron productos")
                    .data(e.getMessage())
                    .build();
        }
    }

    /* List product by id*/
    public ResponseEntity<Product> getProductById(Long productId) throws CustomErrorException {
        try {
            Product product = productRepository.findById(productId).orElseThrow();
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            throw CustomErrorException.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("Producto no encontrado")
                    .data(e.getMessage())
                    .build();
        }
    }

    /* Save product*/

    public ResponseEntity<Product> saveProduct(Product product) throws CustomErrorException {
        try {
            double productProfit = product.getProductSalePrice() - product.getProductPurchasePrice();

            if (productProfit < 0) {
                throw CustomErrorException.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message("El precio de venta no puede ser menor al precio de compra")
                        .build();
            }

            product.setProductProfit(productProfit);

            return new ResponseEntity<>(productRepository.save(product), HttpStatus.CREATED);

        } catch (Exception e) {
            throw CustomErrorException.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("No se pudo guardar el producto")
                    .data(e.getMessage())
                    .build();
        }
    }

    /* Update product*/
    public ResponseEntity<Product> updateProduct(Product product) throws CustomErrorException {
        try {
            Product existingProduct = productRepository.findById(product.getProductId()).orElseThrow();
            product.setCreatedAt(existingProduct.getCreatedAt());
            return new ResponseEntity<>(productRepository.save(product), HttpStatus.OK);
        } catch (Exception e) {
            throw CustomErrorException.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("No se pudo actualizar el producto")
                    .data(e.getMessage())
                    .build();
        }
    }

    /* Delete product by id*/
    public ResponseEntity<Product> deleteProductById(Long productId) throws CustomErrorException {
        try {
            Product product = productRepository.findById(productId).orElseThrow();
            product.setActive(false);
            return new ResponseEntity<>(productRepository.save(product), HttpStatus.OK);
        } catch (Exception e) {
            throw CustomErrorException.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("Producto no encontrado")
                    .data(e.getMessage())
                    .build();
        }
    }
}
