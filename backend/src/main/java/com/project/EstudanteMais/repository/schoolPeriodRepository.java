package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.schoolPeriods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface schoolPeriodRepository extends JpaRepository<schoolPeriods, UUID> {
    @Query(value = "SELECT * FROM school_periods WHERE period_type = ?1 ORDER BY init_period_date DESC LIMIT 1;",nativeQuery = true)
    schoolPeriods getLastInitDate(int period_type);
}
