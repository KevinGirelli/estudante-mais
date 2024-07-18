package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.subjects;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface subjectsRepository extends JpaRepository<subjects, UUID> {
  subjects findBysubjectID(UUID subjectid);

  subjects findBysubjectname(String name);
}
