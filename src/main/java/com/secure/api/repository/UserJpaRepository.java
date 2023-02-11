package com.secure.api.repository;

import com.secure.api.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
