package com.shekhar.shopeasy.repository;

import com.shekhar.shopeasy.entity.Role;
import com.shekhar.shopeasy.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleType name);
}