package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.schoolAdmin;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface adminRepository extends JpaRepository<schoolAdmin, UUID> {
  UserDetails findBydirectorEmail(String email);

  UserDetails findBytwoStepCode(String code);

  @Modifying
  @Transactional
  @Query(value = "UPDATE school_admin set two_step_code = ?1 WHERE adminid = ?2",nativeQuery = true)
  void updateTwoStepCode(String code,UUID adminID);
}
