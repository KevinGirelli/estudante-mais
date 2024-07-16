package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.dto.studentDataDTO;
import com.project.EstudanteMais.Entity.student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public interface studentRepository extends JpaRepository<student, UUID> {
    UserDetails findBystudentEmailOrStudentRegistration(String email, String registration);

    UserDetails findBytwoStepCode(String code);

    @Modifying
    @Transactional
    @Query(value = "UPDATE student set two_step_code = ?1 WHERE studentID = ?2",nativeQuery = true)
    void updateTwoStepCode(String code,UUID studentID);
    @Query(value = "SELECT studentid, student_fullname,student_email,studentcpf,student_age from student WHERE classes_classid = ?1",nativeQuery = true)
    List<Object> getAllStudentFromClass(UUID classID);
}
