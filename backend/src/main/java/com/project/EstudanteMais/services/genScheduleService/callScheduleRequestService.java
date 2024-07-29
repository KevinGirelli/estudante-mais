package com.project.EstudanteMais.services.genScheduleService;
import com.google.gson.Gson;
import com.project.EstudanteMais.Entity.classes;
import com.project.EstudanteMais.Entity.dto.subjectsDTO;
import com.project.EstudanteMais.repository.classesRepository;
import com.project.EstudanteMais.services.configPreferencesService;
import com.project.EstudanteMais.services.genScheduleService.JsonModel.datamodelDTO;
import com.project.EstudanteMais.services.genScheduleService.JsonModel.excelToJsonModel;
import com.project.EstudanteMais.services.genScheduleService.JsonModel.jsonClasses;
import com.project.EstudanteMais.services.genScheduleService.JsonModel.models.classDTO;
import com.project.EstudanteMais.services.genScheduleService.JsonModel.models.settingsDTO;
import com.project.EstudanteMais.services.genScheduleService.JsonModel.models.subjectDTO;
import com.project.EstudanteMais.services.genScheduleService.JsonModel.models.subjectGroupDTO;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class callScheduleRequestService {

  @Autowired
  configPreferencesService configPreferencesService;

  @Autowired
  classesRepository classesRepository;

  public void callRequest(){
    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpPost httpPost = new HttpPost("http://localhost:5000/genSchedule");
    httpPost.setHeader("Content-Type", "application/json");

    Gson gson = new Gson();
    try{
      HttpEntity stringEntity = new StringEntity(gson.toJson(this.configPreferencesService.getScheduleModel()));
      httpPost.setEntity(stringEntity);
      CloseableHttpResponse response = null;

      response = httpClient.execute(httpPost);
      HttpEntity entity = response.getEntity();
      InputStream inputStream = entity.getContent();
      saveFile(inputStream, "Schedule.xlsx");
    }catch(Exception erro){
      System.out.println(erro);
    }
  }

  public excelToJsonModel convertExcelToJson() throws IOException {
    List<classes> allClasses = this.classesRepository.findAll();
    int classesLength = allClasses.size();

    FileInputStream file = new FileInputStream("Schedule.xlsx");
    Workbook workbook = new XSSFWorkbook(file);
    excelToJsonModel jsonModel = new excelToJsonModel();

    Sheet sheet = workbook.getSheetAt(0);
    List<Row> rows = IteratorUtils.toList(sheet.iterator());
    List<String> hours = new ArrayList<>();
    List<jsonClasses> classesSchedule = new ArrayList<>();
    int columnIndex = 1;

    for(int i = 0; i <= classesLength; i++){
      List<String> schedule = new ArrayList<>();
      for (Row row : sheet) {
        Cell cell = row.getCell(0);
        Cell otherCells = row.getCell(columnIndex);
        if(i == 0){
          if (cell != null) {
            hours.add(cell.getStringCellValue());
          }
        }

        if(otherCells != null){
          schedule.add(otherCells.getStringCellValue());
        }
      }
      jsonClasses add = new jsonClasses(schedule);
      classesSchedule.add(add);
      jsonModel.setClasses(classesSchedule);
      columnIndex++;
    }

    jsonModel.setClasses(classesSchedule);
    jsonModel.setHours(hours);
    return jsonModel;
  }
  private static void saveFile(InputStream inputStream, String destFilePath) throws IOException {
    try (FileOutputStream outputStream = new FileOutputStream(new File(destFilePath))) {
      byte[] buffer = new byte[8192];
      int bytesRead;

      while ((bytesRead = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, bytesRead);
      }
    } finally {
      inputStream.close();
    }
  }
}
