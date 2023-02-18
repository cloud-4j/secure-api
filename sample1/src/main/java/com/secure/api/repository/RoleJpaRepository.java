package com.secure.api.repository;

import com.secure.api.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);
}