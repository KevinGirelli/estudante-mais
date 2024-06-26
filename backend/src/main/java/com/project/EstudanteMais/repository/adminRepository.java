package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.schoolAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface adminRepository extends JpaRepository<schoolAdmin, UUID> {
  UserDetails findBydirectorEmail(String email);
}
