package com.project.EstudanteMais.controllers.CrisAssistant;

import com.project.EstudanteMais.repository.studentRepository;
import com.project.EstudanteMais.repository.teacherRepository;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/CrisAssistant")
public class crisController {

  @Autowired
  studentRepository studentRepository;

  @Autowired
  teacherRepository teacherRepository;

  @PostMapping("/validatePhone/{userID}")
  public ResponseEntity validatePhone(@PathVariable(value = "userID")String userID) throws IOException {
    var getStudent = this.studentRepository.findBystudentID(UUID.fromString(userID));
    var getTeacher = this.teacherRepository.findByteacherID(UUID.fromString(userID));

    if(getStudent != null){
      var phone = getStudent.getPhoneNumber();

      CloseableHttpClient httpClient = HttpClients.createDefault();
      HttpPost httpPost = new HttpPost("http://127.0.0.1:5000/validatePhoneNumber/" + phone);
      var response = httpClient.execute(httpPost);

      if(response.getStatusLine().getStatusCode() != 200){
        return ResponseEntity.badRequest().build();
      }
      return ResponseEntity.ok().build();
    }

    if(getTeacher != null){
      var phone = getTeacher.getPhoneNumber();

      CloseableHttpClient httpClient = HttpClients.createDefault();
      HttpPost httpPost = new HttpPost("http://127.0.0.1:5000/validatePhoneNumber/" + phone);
      var response = httpClient.execute(httpPost);

      if(response.getStatusLine().getStatusCode() != 200){
        return ResponseEntity.badRequest().build();
      }

      return ResponseEntity.ok().build();
    }

    return ResponseEntity.internalServerError().build();
  }
}
