package com.tharun.employeetaskmanagement.repository;

import com.tharun.employeetaskmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository for accessing User data in the database.
// JpaRepository provides common database operations automatically.
public interface UserRepository extends JpaRepository<User, Long> {
}

/*UserRepository
     ↓
JpaRepository
     ↓
Provides methods like:
save()
findById()
findAll()
deleteById()
existsById()
      ↓
User table in MySQL*/