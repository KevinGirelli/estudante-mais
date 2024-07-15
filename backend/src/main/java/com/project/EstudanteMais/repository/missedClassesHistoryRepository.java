package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.missedClassesHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface missedClassesHistoryRepository extends JpaRepository<missedClassesHistory, UUID> {
}
