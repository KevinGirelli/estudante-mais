package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.attendence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface attendenceRepository extends JpaRepository<attendence, UUID> {
}
