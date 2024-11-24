package com.project.EstudanteMais.services;

import com.project.EstudanteMais.Entity.schoolAdmin;
import com.project.EstudanteMais.repository.adminRepository;
import com.project.EstudanteMais.repository.studentRepository;
import com.project.EstudanteMais.repository.teacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class authorizationService implements UserDetailsService {

  @Autowired
  adminRepository adminRepository;

  @Autowired
  studentRepository studentRepository;

  @Autowired
  teacherRepository teacherRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserDetails admin = this.adminRepository.findBydirectorEmail(username);
    UserDetails student = this.studentRepository.findBystudentEmailOrStudentRegistration(username, username);
    UserDetails teacher = this.teacherRepository.findByteacherEmail(username);

    if (admin != null) {
      return admin;
    }
    if (student != null && student.isEnabled()) {
      return student;
    }else if(teacher != null && teacher.isEnabled()){
      return teacher;
    }
    return null;
  }
}
