package com.example.registrationsystem.repository;

import com.example.registrationsystem.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository that stores {@link Role} entities
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
