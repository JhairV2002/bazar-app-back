package jv.bazar.amacame.repositories;

import jv.bazar.amacame.entity.Promos;
import jv.bazar.amacame.enums.PromoTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromoRepository extends JpaRepository<Promos, Long>{
    List<Promos> findByisActive(boolean status);
     Promos findByPromoIdAndPromoType(Long promoId, PromoTypeEnum promoType);
}
