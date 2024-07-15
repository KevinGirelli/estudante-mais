package com.project.EstudanteMais.services;

import com.project.EstudanteMais.repository.adminRepository;
import com.project.EstudanteMais.repository.teacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class RandomCodeService {
  @Autowired
  teacherRepository teacherRepository;

  @Autowired
  adminRepository adminRepository;

  @Autowired
  configPreferencesService configPreferencesService;

  private Random random = new Random();
  public String GenerateCode(){
    String n1 = Integer.toString(random.nextInt(10));
    String n2 = Integer.toString(random.nextInt(10));
    String n3 = Integer.toString(random.nextInt(10));
    String n4 = Integer.toString(random.nextInt(10));
    String n5 = Integer.toString(random.nextInt(10));
    String n6 = Integer.toString(random.nextInt(10));

    String code = n1+n2+n3+n4+n5+n6;
    return code;
  }

  public String verifyCode(String codeToRemove){
    String secondCode = "";
    List<String> allCodes = this.configPreferencesService.getActiveCodes();

    if(this.configPreferencesService.getActiveCodes().contains(codeToRemove)){
      secondCode = GenerateCode();
    }
    if (secondCode == "") {
      allCodes.add(codeToRemove);
      this.configPreferencesService.setActiveCodes(allCodes);
      return codeToRemove;
    }else{
      allCodes.add(secondCode);
      this.configPreferencesService.setActiveCodes(allCodes);
      return secondCode;
    }
  }
}
