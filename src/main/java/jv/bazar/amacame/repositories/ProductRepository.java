package jv.bazar.amacame.repositories;

import jv.bazar.amacame.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findByIsActive(boolean isActive);
    Product findByProductName(String productName);
    Product findByProductNameAndIsActive(String productName, boolean isActive);
    Product findByProductIdAndIsActive(Long productId, boolean isActive);

    List<Product> findByIsActiveAndProductBrand_isActive(boolean isActiveProduct, boolean isActiveBrand);
}
