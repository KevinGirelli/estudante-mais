package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.teacher;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

public interface teacherRepository extends JpaRepository<teacher, UUID> {

    @Query(value = "SELECT * FROM teacher WHERE teacher_enable = 1",nativeQuery = true)
    List<teacher> findAllTeachersEnable();

    teacher findByteacherID(UUID id);
    UserDetails findByteacherEmail(String email);

    teacher findByphoneNumber(String phone);

    teacher findByteacherRegistration(String registration);

    UserDetails findBytwoStepCode(String code);

    @Modifying
    @Transactional
    @Query(value = "UPDATE teacher set two_step_code = ?1 WHERE teacherid = ?2",nativeQuery = true)
    void updateTwoStepCode(String code,UUID teacherID);

    @Modifying
    @Transactional
    @Query(value = "UPDATE teacher SET teacher_name = ?1, teacher_email = ?2, teachercpf = ?3, teacher_working_days = ?4 WHERE teacherid = ?5",nativeQuery = true)
    void updateTeacherPrimaryData(String name, String email, String cpf, String teacherWorkingDays, UUID teacherID);

    @Modifying
    @Transactional
    @Query(value = "UPDATE teacher SET teacher_password = ?1 WHERE teacherid = ?2",nativeQuery = true)
    void updateTeacherPassword(String encodedPassword, UUID teacherID);

    @Modifying
    @Transactional
    @Query(value = "UPDATE teacher SET twostepverification = ?1 WHERE teacherid = ?2",nativeQuery = true)
    void updateTwoStepVerifyState(boolean state, UUID studentID);

    @Modifying
    @Transactional
    @Query(value = "UPDATE teacher SET teacher_enable = 0 WHERE teacherid = ?1", nativeQuery = true)
    void disableTeacher(UUID teacherID);

    @Modifying
    @Transactional
    @Query(value = "UPDATE teacher SET teacher_enable = 1 WHERE teacherid = ?1", nativeQuery = true)
    void enableTeacher(UUID teacherID);
}
