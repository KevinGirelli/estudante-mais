package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface studentRepository extends JpaRepository<student, UUID> {
    UserDetails findBystudentEmailOrStudentRegistration(String email, String registration);
}
