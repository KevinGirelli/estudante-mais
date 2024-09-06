package com.project.EstudanteMais.infra.security;

import com.project.EstudanteMais.repository.adminRepository;
import com.project.EstudanteMais.repository.studentRepository;
import com.project.EstudanteMais.repository.teacherRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class internalFilter extends OncePerRequestFilter {

  @Autowired
  TokenService tokenService;

  @Autowired
  studentRepository studentRepository;

  @Autowired
  teacherRepository teacherRepository;

  @Autowired
  adminRepository adminRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    var token = this.recoverToken(request);
    if(token != null){
       var subject = tokenService.validateToken(token);
        UserDetails admin = this.adminRepository.findBydirectorEmail(subject);
        UserDetails student = this.studentRepository.findBystudentEmailOrStudentRegistration(subject,subject);
        UserDetails teacher  = this.teacherRepository.findByteacherEmail(subject);
        if(admin != null){
            var auth = new UsernamePasswordAuthenticationToken(admin.getUsername(), admin.getUsername(),admin.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        if(student != null){
            var auth = new UsernamePasswordAuthenticationToken(student.getUsername(), student.getUsername(),student.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        if(teacher != null){
            var auth = new UsernamePasswordAuthenticationToken(teacher.getUsername(), teacher.getUsername(),teacher.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

    }
    filterChain.doFilter(request,response);
  }

  private String recoverToken(HttpServletRequest request){
    var authHeader = request.getHeader("Authorization");
    if(authHeader == null) return null;
    return authHeader.replace("Bearer ","");
  }
}
