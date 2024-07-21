package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.assessment;
import com.project.EstudanteMais.Entity.teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface assessmentRepository extends JpaRepository<assessment, UUID> {

  List<assessment> findByteacher(teacher teacher);
}
