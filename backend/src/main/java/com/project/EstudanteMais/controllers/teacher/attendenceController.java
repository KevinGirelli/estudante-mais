package com.project.EstudanteMais.controllers.teacher;

import com.project.EstudanteMais.Entity.*;
import com.project.EstudanteMais.Entity.dto.registryAttendenceDTO;
import com.project.EstudanteMais.repository.attendenceRepository;
import com.project.EstudanteMais.repository.classesRepository;
import com.project.EstudanteMais.repository.studentRepository;
import com.project.EstudanteMais.repository.teacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/attendence")
public class attendenceController {

  @Autowired
  studentRepository studentRepository;

  @Autowired
  classesRepository classesRepository;

  @Autowired
  teacherRepository teacherRepository;

  @Autowired
  attendenceRepository attendenceRepository;

  @PostMapping("/registryAttendence")
  public ResponseEntity registryNewAttendence(@RequestBody registryAttendenceDTO data){
    classes getClass = this.classesRepository.findByclassID(UUID.fromString(data.classID()));
    teacher getTeacher = this.teacherRepository.findByteacherID(UUID.fromString(data.teacherID()));
    data.students().forEach(students ->{
      var split = students.split(",");
      student getStudent = this.studentRepository.findBystudentID(UUID.fromString(split[0]));

      var splitDate = data.date().split("T");
      var splitData2 = splitDate[0];
      var splitData3 = splitData2.split("-");
      var formattedDate = splitData3[2] + "/" + splitData3[1] + "/" +splitData3[0];

      attendence newAtt = new attendence(getStudent,getClass,getTeacher,
              attendenceStatus.valueOf(split[1]),data.quantity(),formattedDate);

      this.attendenceRepository.save(newAtt);
    });
    return ResponseEntity.ok().build();
  }

  @GetMapping("/getAllStudentsFromClass/{classID}")
  public ResponseEntity getAllStudentsFromClass(@PathVariable(value = "classID") String id){
    var allStudents = this.studentRepository.getAllStudentFromClass(UUID.fromString(id));
    if(allStudents != null){
      return ResponseEntity.ok(allStudents);
    }else{
      return ResponseEntity.internalServerError().build();
    }
  }
}
