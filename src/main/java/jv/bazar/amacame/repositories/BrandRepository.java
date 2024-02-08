package jv.bazar.amacame.repositories;

import jv.bazar.amacame.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand findByBrandName(String brandName);
    List<Brand> findByIsActive(boolean isActive);

    Brand findByBrandNameAndIsActive(String brandName, boolean isActive);

    Brand findByBrandIdAndIsActive(Long brandId, boolean isActive);

}
