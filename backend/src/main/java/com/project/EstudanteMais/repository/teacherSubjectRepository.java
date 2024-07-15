package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.teacherSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface teacherSubjectRepository extends JpaRepository<teacherSubject, UUID> {
}
