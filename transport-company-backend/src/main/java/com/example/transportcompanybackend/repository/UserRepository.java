package com.example.transportcompanybackend.repository;

import com.example.transportcompanybackend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
        SELECT u FROM User u
        WHERE (:id IS NULL OR u.id = :id)
        AND (:email IS NULL OR LOWER(u.email) LIKE CONCAT('%', LOWER(:email), '%'))
        AND (:firstName IS NULL OR LOWER(u.firstName) LIKE CONCAT('%', LOWER(:firstName), '%'))
        AND (:lastName IS NULL OR LOWER(u.lastName) LIKE CONCAT('%', LOWER(:lastName), '%'))
        AND (:role IS NULL OR u.role = :role)
        AND (:isDisabled IS NULL OR u.isDisabled = :isDisabled)
        """)
    Page<User> findAllBy(Long id, String email, String firstName, String lastName, User.Role role, Boolean isDisabled, Pageable pageable);

    Optional<User> findByEmail(String email);
}
