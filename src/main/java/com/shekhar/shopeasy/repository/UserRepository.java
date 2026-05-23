package com.shekhar.shopeasy.repository;

import com.shekhar.shopeasy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 1
    boolean existsByEmail(String email);

    // 2
    boolean existsByPhoneNumber(String phoneNumber);

    // 3
    Optional<User> findByEmail(String email);

    // 4
    Optional<User> findByPhoneNumber(String phoneNumber);
}