package com.secure.api.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import com.secure.api.entity.RoleEntity;
import com.secure.api.entity.UserEntity;
import com.secure.api.repository.RoleJpaRepository;
import com.secure.api.repository.UserJpaRepository;
import com.secure.api.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private static final String USER_NOT_FOUND_MESSAGE = "User with username %s not found";

    private final UserJpaRepository userJpaRepository;
    private final RoleJpaRepository roleJpaRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userJpaRepository.findByUsername(username);
        if (user == null) {
            String message = String.format(USER_NOT_FOUND_MESSAGE, username);
            log.error(message);
            throw new UsernameNotFoundException(message);
        } else {
            log.debug("User found in the database: {}", username);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            return new User(user.getUsername(), user.getPassword(), authorities);
        }
    }

    public UserEntity save(UserEntity user) {
        log.info("Saving user {} to the database", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userJpaRepository.save(user);
    }

    public UserEntity addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        UserEntity userEntity = userJpaRepository.findByUsername(username);
        RoleEntity roleEntity = roleJpaRepository.findByName(roleName);
        userEntity.getRoles().add(roleEntity);
        return userEntity;
    }

    @Transactional(readOnly = true)
    public UserEntity findByUsername(String username) {
        log.info("Retrieving user {}", username);
        return userJpaRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public List<UserEntity> findAll() {
        log.info("Retrieving all users");
        return userJpaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Map<String, String> refreshToken(String authorizationHeader, String issuer) throws BadJOSEException,
            ParseException, JOSEException {
        String refreshToken = authorizationHeader.substring("Bearer ".length());
        UsernamePasswordAuthenticationToken authenticationToken = JwtUtil.parseToken(refreshToken);
        String username = authenticationToken.getName();
        UserEntity userEntity = findByUsername(username);
        List<String> roles = userEntity.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toList());
        String accessToken = JwtUtil.createAccessToken(username, issuer, roles);
        return Map.of("access_token", accessToken, "refresh_token", refreshToken);
    }

}

