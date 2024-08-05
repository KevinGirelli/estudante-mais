package com.project.EstudanteMais.services;

import org.springframework.stereotype.Service;

@Service
public class formatDateService {

  public String formatDate(String data){
    var splitDate = data.split("T");
    var splitData2 = splitDate[0];
    var splitData3 = splitData2.split("-");
    var formattedDate = splitData3[2] + "/" + splitData3[1] + "/" +splitData3[0];
    return formattedDate;
  }
}
