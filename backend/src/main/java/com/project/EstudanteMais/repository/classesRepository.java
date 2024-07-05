package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.classes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface classesRepository extends JpaRepository<classes, UUID> {
}
