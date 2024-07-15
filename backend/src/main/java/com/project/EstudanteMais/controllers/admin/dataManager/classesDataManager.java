package com.project.EstudanteMais.controllers.admin.dataManager;


import com.project.EstudanteMais.Entity.dto.classesDTO;
import com.project.EstudanteMais.repository.classesRepository;
import com.project.EstudanteMais.services.UUIDformatter;
import com.project.EstudanteMais.services.configPreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/classesDataManager")
public class classesDataManager {

  @Autowired
  configPreferencesService configService;

  @Autowired
  UUIDformatter uuiDformatter;

  @Autowired
  classesRepository classesRepository;

  @GetMapping("/getClassesAsync")
  public ResponseEntity getClasses(){
      var allClasses = this.classesRepository.findAll();
      List<classesDTO> allClassesDTO = new ArrayList<>();

      allClasses.forEach(classes -> {
          classesDTO classesDTO = new classesDTO(uuiDformatter.formatUuid(classes.getClassID()),classes.getClassName());
          allClassesDTO.add(classesDTO);
      });
      return ResponseEntity.ok(allClassesDTO);
    }
}
