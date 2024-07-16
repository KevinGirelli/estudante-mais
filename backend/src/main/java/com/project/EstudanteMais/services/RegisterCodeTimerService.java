package com.project.EstudanteMais.services;


import com.project.EstudanteMais.repository.adminRepository;
import com.project.EstudanteMais.repository.studentRepository;
import com.project.EstudanteMais.repository.teacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class RegisterCodeTimerService {
  @Autowired
  studentRepository studentRepository;

  @Autowired
  teacherRepository teacherRepository;

  @Autowired
  adminRepository adminRepository;

  @Autowired
  configPreferencesService configPreferencesService;

  @Autowired
  RandomCodeService randomCodeService;

  @Async
  public void StartTRegisterTimer(String codeToRemove){
    try {
      Thread.sleep(100000);
      this.configPreferencesService.getActiveCodes().remove(codeToRemove);
    }catch (InterruptedException e){
      Thread.currentThread().interrupt();
    }
    }
  }
