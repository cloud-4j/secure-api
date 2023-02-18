package com.secure.api.config;

import com.secure.api.entity.RoleEntity;
import com.secure.api.entity.UserEntity;
import com.secure.api.service.RoleService;
import com.secure.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@Log
@Configuration
@RequiredArgsConstructor
public class LoadDatabase {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService, RoleService roleService) {
        return args -> {
            roleService.save(new RoleEntity(null, "ROLE_USER"));
            roleService.save(new RoleEntity(null, "ROLE_ADMIN"));

            userService.save(new UserEntity(null, "bob", "1234", new ArrayList<>()));
            userService.save(new UserEntity(null, "alice", "1234", new ArrayList<>()));

            userService.addRoleToUser("bob", "ROLE_USER");
            userService.addRoleToUser("alice", "ROLE_ADMIN");
            userService.addRoleToUser("alice", "ROLE_USER");
        };
    }
}
