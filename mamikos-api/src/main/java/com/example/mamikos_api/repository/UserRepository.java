package com.example.mamikos_api.repository;

import com.example.mamikos_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //mencari user berdasarkan username
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
}