package com.project.EstudanteMais.controllers.teacher;

import com.project.EstudanteMais.Entity.dto.subjectsDTO;
import com.project.EstudanteMais.Entity.teacherSubject;
import com.project.EstudanteMais.repository.subjectsRepository;
import com.project.EstudanteMais.repository.teacherRepository;
import com.project.EstudanteMais.repository.teacherSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/teacher")
public class teacherController {

  @Autowired
  teacherRepository teacherRepository;

  @Autowired
  subjectsRepository subjectsRepository;

  @Autowired
  teacherSubjectRepository teacherSubjectRepository;
  @GetMapping("/getTeacherSubject/{teacherID}")
  public ResponseEntity getTeacherSubject(@PathVariable(value = "teacherID")String teacherID){
    List<teacherSubject> teacherSubjects = this.teacherSubjectRepository.findByteacher(this.teacherRepository.findByteacherID(UUID.fromString(teacherID)));
    subjectsDTO subjects = new subjectsDTO();
    if(teacherSubjects != null){
        List<String> temp = new ArrayList<>();
        teacherSubjects.forEach(subject ->{
          temp.add(subject.getSubject().getSubjectID().toString() + "," + subject.getSubject().getSubjectname());
        });
        subjects.setSubjectsIDS(temp);
        return ResponseEntity.ok(subjects);
    }

    return ResponseEntity.internalServerError().build();
  }
}
