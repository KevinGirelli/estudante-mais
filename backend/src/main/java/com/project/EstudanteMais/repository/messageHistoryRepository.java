package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.messagesHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface messageHistoryRepository extends JpaRepository<messagesHistory, UUID> {
  @Query(value = "SELECT * FROM messages_history WHERE classes_classid = ?1 AND teacher_teacherid = ?2 ORDER BY registration_date ASC " +
          "",nativeQuery = true)
  List<messagesHistory> findAllByRegistrationDate(UUID classID, UUID teacherID);
}
