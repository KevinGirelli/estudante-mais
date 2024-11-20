package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.classes;
import com.project.EstudanteMais.Entity.dto.studentDataDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface classesRepository extends JpaRepository<classes, UUID> {
  classes findByclassName(String className);
  classes findByclassID(UUID id);

  @Modifying
  @Transactional
  @Query(value = "UPDATE classes set class_name = ?1, grade_number = ?2, grade_type = ?3, period_type = ?4 WHERE classid = ?5",nativeQuery = true)
  void updateClassPrimaryData(String className, int gradeNumber, String gradeType, int periodType, UUID classID);


  @Query(name = "Student.getAllStudentFromClass",nativeQuery = true)
  List<studentDataDTO> getAllStudentFromClass(UUID classID);

}
