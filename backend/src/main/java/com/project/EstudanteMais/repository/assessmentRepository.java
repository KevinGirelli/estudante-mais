package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.assessment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface assessmentRepository extends JpaRepository<assessment, UUID> {
}
