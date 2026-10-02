package com.tharun.employeetaskmanagement.repository;

import com.tharun.employeetaskmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
 * Repository used to access User data in the database.
 * JpaRepository provides common CRUD operations automatically.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /*
     * Finds a User using the email address.
     * Optional is used because the email may not exist.
     */
    Optional<User> findByEmail(String email);
}