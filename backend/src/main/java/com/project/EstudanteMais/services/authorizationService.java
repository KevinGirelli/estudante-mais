package com.project.EstudanteMais.services;

import com.project.EstudanteMais.repository.adminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class authorizationService implements UserDetailsService {

  @Autowired
  adminRepository adminRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return this.adminRepository.findBydirectorEmail(username);
  }
}
