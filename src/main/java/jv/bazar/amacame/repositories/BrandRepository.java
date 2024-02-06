package jv.bazar.amacame.repositories;

import jv.bazar.amacame.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand findByBrandName(String brandName);
    Brand findByIsActive(boolean isActive);

    Brand findByBrandNameAndIsActive(String brandName, boolean isActive);
}
