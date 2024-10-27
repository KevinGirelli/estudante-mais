package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.schoolSettings;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface schoolSettingsRepository extends JpaRepository<schoolSettings, UUID> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE school_settings SET report_card_active_bimestral_date = ?1," +
            "report_card_active_trimestral_date = ?2," +
            "report_card_active_semestral_date = ?3," +
            "evaluate_teacher_date = ?4",nativeQuery = true)
    void updateDateSettings(String biDate,String triDate, String semDate, String evaluateTeacher);

    //Update schoolPeriod UUID
    @Modifying
    @Transactional
    @Query(value = "UPDATE school_settings SET current_bi_periodid = ?1",nativeQuery = true)
    void updateCurrentBimestre(UUID currentBi);

    @Modifying
    @Transactional
    @Query(value = "UPDATE school_settings SET current_tri_periodid = ?1",nativeQuery = true)
    void updateCurrentTrimestre(UUID currentTri);

    @Modifying
    @Transactional
    @Query(value = "UPDATE school_settings SET current_sem_periodid = ?1",nativeQuery = true)
    void updateCurrentSemestre(UUID currentSem);

    //Update periodNumbers

    @Modifying
    @Transactional
    @Query(value = "UPDATE school_settings SET bimestral_number = ?1",nativeQuery = true)
    void updateBimestralNumber(int number);

    @Modifying
    @Transactional
    @Query(value = "UPDATE school_settings SET trimestral_number = ?1",nativeQuery = true)
    void updateTrimestralNumber(int number);

    @Modifying
    @Transactional
    @Query(value = "UPDATE school_settings SET semestral_number = ?1",nativeQuery = true)
    void updateSemestralNumber(int number);



}
