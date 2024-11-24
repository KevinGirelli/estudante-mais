package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.attendence;
import com.project.EstudanteMais.Entity.attendenceHistory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface attendenceHistoryRepository extends JpaRepository<attendenceHistory, UUID> {
  List<attendenceHistory> findByregistrationDate(String missed);

  attendenceHistory findBymissedClasses(attendence attendence);

  @Transactional
  @Modifying
  @Query(value = "DELETE FROM attendence_history WHERE registrationid = ?1", nativeQuery = true)
  void deleteHistory(UUID registrationID);

}
