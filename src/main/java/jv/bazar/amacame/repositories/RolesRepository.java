package jv.bazar.amacame.repositories;

import jv.bazar.amacame.entity.Roles;
import jv.bazar.amacame.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findRolesByRoleEnum(RoleEnum roleEnum);
    Set<Roles> findRolesByRoleEnumIn(Set<RoleEnum> roleEnums);
}
