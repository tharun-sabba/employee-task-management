package com.tharun.employeetaskmanagement.repository;

import com.tharun.employeetaskmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}