package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.missedClasses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface missedClassesRepository extends JpaRepository<missedClasses, UUID> {
}
