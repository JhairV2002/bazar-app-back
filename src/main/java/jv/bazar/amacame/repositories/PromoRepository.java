package jv.bazar.amacame.repositories;

import jv.bazar.amacame.entity.Promos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromoRepository extends JpaRepository<Promos, Long>{
    public List<Promos> findByisActive(boolean status);
}
