package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.classDiary;
import com.project.EstudanteMais.Entity.classes;
import com.project.EstudanteMais.Entity.subjects;
import com.project.EstudanteMais.Entity.teacher;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface classDiaryRepository extends JpaRepository<classDiary, UUID> {
  classDiary findByregistryDateAndClassesAndTeacherAndSubjects(String registryDate, classes classes, teacher teacher, subjects subject);

  List<classDiary> findByTeacherAndClasses(teacher teacher, classes classes);

  @Modifying
  @Transactional
  @Query(value = "UPDATE class_diary SET day_content = ?1, observations = ?2," +
          "registry_date = ?3, subjects_subjectid = ?4 WHERE dayid = ?5",nativeQuery = true)
  void updateClassDiary(
          String dayContent, String observations, String registryDate, UUID subjectID, UUID dayid
  );
}
