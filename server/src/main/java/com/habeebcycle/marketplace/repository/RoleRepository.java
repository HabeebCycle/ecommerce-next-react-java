package com.habeebcycle.marketplace.repository;

import com.habeebcycle.marketplace.model.common.RoleName;
import com.habeebcycle.marketplace.model.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName roleName);

}
