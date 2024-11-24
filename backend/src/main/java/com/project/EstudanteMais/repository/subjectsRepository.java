package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.subjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface subjectsRepository extends JpaRepository<subjects, UUID> {
  subjects findBysubjectID(UUID subjectid);

  subjects findBysubjectname(String name);

  @Query(value = "SELECT * FROM subjects WHERE subject_period = ?1",nativeQuery = true)
  List<subjects> findByPeriodType(int period);
}
