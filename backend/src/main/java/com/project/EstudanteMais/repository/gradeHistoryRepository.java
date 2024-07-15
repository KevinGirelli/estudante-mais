package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.gradeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface gradeHistoryRepository extends JpaRepository<gradeHistory, UUID> {
}
