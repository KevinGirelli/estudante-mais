package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.attendence;
import com.project.EstudanteMais.Entity.attendenceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface attendenceHistoryRepository extends JpaRepository<attendenceHistory, UUID> {
  List<attendenceHistory> findByregistrationDate(String missed);
}
