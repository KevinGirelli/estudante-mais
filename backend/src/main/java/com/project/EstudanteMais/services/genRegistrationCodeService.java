package com.project.EstudanteMais.services;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class genRegistrationCodeService {
    public String genCode(String studentName){
      Random rand = new Random();
      String registrationCode = "";

      for(int i = 1; i <= 12; i++){
        if(i == 7){
          registrationCode += ".";
        }else if(i == 11){
          registrationCode += "-";
        }else if(i == 12){
          registrationCode += Character.toString(studentName.charAt(0));
        }

        if(i != 7 && i != 11 && i != 12){
          registrationCode += Integer.toString(rand.nextInt(9));
        }
      }
      return registrationCode;
    }
}
