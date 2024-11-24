package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.classes;
import com.project.EstudanteMais.Entity.classes_subjects;
import com.project.EstudanteMais.Entity.subjects;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface classes_subjectsRepository extends JpaRepository<classes_subjects, UUID> {

  List<classes_subjects> findBysubjects(subjects subject);

  List<classes_subjects> findBySubjectsAndClasses(subjects subject,classes classes);

  List<classes_subjects> findByclasses(classes classes);

  @Modifying
  @Transactional
  @Query(value = "UPDATE classes_subjects SET number_of_classes = ?1 WHERE class_subjectid = ?2",nativeQuery = true)
  void updateQuantityOfClasses(int number, UUID classSubjectID);

  @Transactional
  @Modifying
  @Query(value = "DELETE FROM classes_subjects WHERE classes_classid = ?1", nativeQuery = true)
  void deleteClass(UUID classID);
}
