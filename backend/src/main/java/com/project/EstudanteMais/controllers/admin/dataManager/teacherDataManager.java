package com.project.EstudanteMais.controllers.admin.dataManager;

import com.project.EstudanteMais.Entity.TeacherClasses;
import com.project.EstudanteMais.Entity.dto.teachersDTO;
import com.project.EstudanteMais.Entity.dto.updateTeacherDataDTO;
import com.project.EstudanteMais.Entity.teacherSubject;
import com.project.EstudanteMais.repository.*;
import com.project.EstudanteMais.services.UUIDformatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/admin/teacherDataManager")
public class teacherDataManager {

    @Autowired
    com.project.EstudanteMais.repository.teacherRepository teacherRepository;

    @Autowired
    com.project.EstudanteMais.repository.teacherSubjectRepository teacherSubjectRepository;

    @Autowired
    com.project.EstudanteMais.repository.subjectsRepository subjectsRepository;

    @Autowired
    com.project.EstudanteMais.repository.classesRepository classesRepository;

    @Autowired
    com.project.EstudanteMais.repository.teacherClassesRepository teacherClassesRepository;

    @Autowired
    classes_subjectsRepository classesSubjectsRepository;

    @Autowired
    com.project.EstudanteMais.services.configPreferencesService configPreferencesService;

    @Autowired
    UUIDformatter uuiDformatter;

    @GetMapping("/getTeacher/{teacherEmail}")
    public ResponseEntity selectTeacher(@PathVariable(value = "teacherEmail") String email) {
        UserDetails gotTeacher = this.teacherRepository.findByteacherEmail(email);

        if (gotTeacher != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body(gotTeacher);
        } else {
            return ResponseEntity.badRequest().body("user not found");
        }
    }

    @GetMapping("/getAllTeachers")
    public ResponseEntity getAllTeachers() {
        var teachers = this.teacherRepository.findAll();
        List<teachersDTO> allTeachers = new ArrayList<>();

        teachers.forEach(teacher -> {
            List<teacherSubject> teacherSubjects = this.teacherSubjectRepository.findByteacher(teacher);
            List<String> listSubjects = new ArrayList<>();

            teacherSubjects.forEach(subject -> {
                listSubjects.add(subject.getSubject().getSubjectname());
            });

            teachersDTO addTeacher = new teachersDTO(this.uuiDformatter.formatUuid(teacher.getTeacherID()), teacher.getTeacherName()
                    , teacher.getTeacherEmail(), teacher.getTeacherCPF(), listSubjects);
            allTeachers.add(addTeacher);
        });
        return ResponseEntity.ok(allTeachers);
    }

    @GetMapping("/getAllTeacherFromClass/{classID}")
    public ResponseEntity getAllTeacherFromClass(@PathVariable(value = "classID") String classID) {
        List<TeacherClasses> teacherClasses = this.teacherClassesRepository.findByclasses(this.classesRepository.findByclassID(UUID.fromString(classID)));
        List<teachersDTO> teachersDTOS = new ArrayList<>();
        System.out.println(this.classesRepository.findByclassID(UUID.fromString(classID)));
        teacherClasses.forEach(teacher -> {
            List<teacherSubject> subjects = this.teacherSubjectRepository.findByteacher(teacher.getTeacher());
            List<String> teacherSubjects = new ArrayList<>();

            subjects.forEach(subject -> {
                teacherSubjects.add(subject.getSubject().getSubjectname());
            });

            teachersDTO add = new teachersDTO(
                    teacher.getTeacher().getTeacherID().toString(),
                    teacher.getTeacher().getTeacherName(),
                    teacher.getTeacher().getTeacherEmail(),
                    teacher.getTeacher().getTeacherCPF(),
                    teacherSubjects
            );
            teachersDTOS.add(add);
        });
        return ResponseEntity.ok(teachersDTOS);
    }


    @PatchMapping("/updateTeacherPrimaryData")
    public ResponseEntity updateTeacherPrimaryData(@RequestBody updateTeacherDataDTO teacherData) {
        var getTeacher = this.teacherRepository.findByteacherID(UUID.fromString(teacherData.teacherID()));
        if (getTeacher != null) {
            if (!getTeacher.getTeacherEmail().equals(teacherData.email())) {
                if (this.teacherRepository.findByteacherEmail(teacherData.email()) != null) {
                    return ResponseEntity.badRequest().build();
                }
            }

            List<teacherSubject> teacherSubjects = this.teacherSubjectRepository.findByteacher(getTeacher);
            teacherData.subjects().forEach(s -> {
                AtomicBoolean found = new AtomicBoolean(false);

                teacherSubjects.forEach(ts -> {
                    if (s.equals(ts.getSubject().getSubjectID().toString())) {
                        found.set(true);
                    }
                });

                if (!found.get()) {
                    var getSubject = this.subjectsRepository.findBysubjectID(UUID.fromString(s));
                    if (getSubject != null) {
                        teacherSubject newRegistry = new teacherSubject(getSubject, getTeacher);
                        this.teacherSubjectRepository.save(newRegistry);
                    }
                }
            });
        }

        teacherData.teacherClasses().forEach(teacherClass -> {
            var split = teacherClass.split(",");
            var getClass = this.classesRepository.findByclassID(UUID.fromString(split[1]));
            var getSubject = this.subjectsRepository.findBysubjectID(UUID.fromString(split[0]));

            if (this.teacherClassesRepository.findByClassesAndSubjects(getClass, getSubject) == null) {
                TeacherClasses newRegistry = new TeacherClasses(getTeacher, getClass, getSubject);
                this.teacherClassesRepository.save(newRegistry);
            }
        });

        teacherData.removeTeacherClasses().forEach(removeTeacher -> {
            var split = removeTeacher.split(",");
            var getClass = this.classesRepository.findByclassID(UUID.fromString(split[1]));
            var getSubject = this.subjectsRepository.findBysubjectID(UUID.fromString(split[0]));

            var verify = this.teacherClassesRepository.findByClassesAndSubjectsAndTeacher(getClass, getSubject, getTeacher);
            if (verify != null) {
                this.teacherClassesRepository.deleteTeacherFromClass(verify.getClasses().getClassID(), verify.getTeacher().getTeacherID(), verify.getSubjects().getSubjectID());
            }
        });

        teacherData.subjectsToRemove().forEach(s -> {
            var getSubject = this.subjectsRepository.findBysubjectID(UUID.fromString(s));
            if (getSubject != null) {
                //Remove from teacherSubject table
                var getTeacherSubject = this.teacherSubjectRepository.findByTeacherAndSubject(getTeacher, getSubject);
                if (getTeacherSubject != null) {
                    this.teacherSubjectRepository.delete(getTeacherSubject);
                }

                //Remove from TeacherClasses table
                var getTeacherClasses = this.teacherClassesRepository.findByteacher(getTeacher);
                getTeacherClasses.forEach(tc -> {
                    if (tc.getSubjects().getSubjectID().toString().equals(getSubject.getSubjectID().toString())) {
                        this.teacherClassesRepository.delete(tc);
                    }
                });
            }
        });

        this.teacherRepository.updateTeacherPrimaryData(teacherData.nome(), teacherData.email(), teacherData.cpf(), UUID.fromString(teacherData.teacherID()));
        return ResponseEntity.ok().build();
    }

}
