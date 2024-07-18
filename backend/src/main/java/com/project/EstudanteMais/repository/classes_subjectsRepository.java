package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.classes_subjects;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface classes_subjectsRepository extends JpaRepository<classes_subjects, UUID> {
}
