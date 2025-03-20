package jv.bazar.amacame.repositories;

import jv.bazar.amacame.entity.BillDetailLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillDetailLineRepository extends JpaRepository<BillDetailLine, Long>{ }
