package com.example.streak.user.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findFirstByNameAndPassword(String name, String password);
    Optional<UserEntity> findFirstByName(String name);
}