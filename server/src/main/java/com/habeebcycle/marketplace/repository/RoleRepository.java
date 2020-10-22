package com.habeebcycle.marketplace.repository;

import com.habeebcycle.marketplace.model.RoleName;
import com.habeebcycle.marketplace.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName roleName);

}
