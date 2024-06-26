package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface teacherRepository extends JpaRepository<teacher, UUID> {
    UserDetails findByteacherEmail(String email);
}
