package com.example.registrationsystem.repository;

import com.example.registrationsystem.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
