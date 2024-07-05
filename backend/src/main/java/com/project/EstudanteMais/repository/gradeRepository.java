package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface gradeRepository extends JpaRepository<grade, UUID> {
}
