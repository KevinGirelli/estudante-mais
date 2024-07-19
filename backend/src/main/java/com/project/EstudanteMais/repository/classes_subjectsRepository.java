package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.classes_subjects;
import com.project.EstudanteMais.Entity.subjects;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface classes_subjectsRepository extends JpaRepository<classes_subjects, UUID> {

  List<classes_subjects> findBysubjects(subjects subject);
}
