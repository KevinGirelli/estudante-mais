package com.project.EstudanteMais.services;

import com.project.EstudanteMais.Entity.schoolPeriods;
import com.project.EstudanteMais.repository.schoolPeriodRepository;
import com.project.EstudanteMais.repository.schoolSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class periodManagerService {
    @Autowired
    schoolSettingsRepository schoolSettingsRepository;

    @Autowired
    schoolPeriodRepository schoolPeriodRepository;

    @Scheduled(cron = "0 1 0 * * ?")
    public void checkDates(){
        System.out.println("Começando checagem de datas");
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = today.format(formatter);

        var settings = this.schoolSettingsRepository.findAll();
        var config = settings.get(0);

        String biDate = config.getReportCardActivateBimestralDate();
        String triDate = config.getReportCardActivateTrimstralDate();
        String semDate = config.getReportCardActivateSemestralDate();
        String evaluateTeacher = config.getEvaluateTeacherDate();

        if(biDate.equals(formattedDate)){
            //Register on database the new current bimestral
            var split = formattedDate.split("/");
            var month = Integer.parseInt(split[1]);
            month += 2;

            if(month > 12) month = 1;

            split[1] = Integer.toString(month);
            System.out.println(String.join("/",split));
            schoolPeriods newPeriod = new schoolPeriods(2,String.join("/",split),formattedDate);
            schoolPeriods save = this.schoolPeriodRepository.save(newPeriod);
            this.schoolSettingsRepository.updateCurrentBimestre(save.getPeriodID());
            this.schoolSettingsRepository.updateDateSettings(
                    String.join("/",split),
                    config.getReportCardActivateTrimstralDate(),
                    config.getReportCardActivateSemestralDate(),
                    config.getEvaluateTeacherDate()
            );

            //Update current periodNumber
            int biNumber = config.getBimestralNumber();

            biNumber++;
            if(biNumber > 6) biNumber = 1;

            this.schoolSettingsRepository.updateBimestralNumber(biNumber);
        }

        if(triDate.equals(formattedDate)){
            var split = formattedDate.split("/");
            var month = Integer.parseInt(split[1]);
            month += 3;

            if(month > 12) month = 1;

            split[1] = Integer.toString(month);
            schoolPeriods newPeriod = new schoolPeriods(3,String.join("/",split),formattedDate);
            schoolPeriods save = this.schoolPeriodRepository.save(newPeriod);
            this.schoolSettingsRepository.updateCurrentTrimestre(save.getPeriodID());
            this.schoolSettingsRepository.updateDateSettings(
                    config.getReportCardActivateBimestralDate(),
                    String.join("/",split),
                    config.getReportCardActivateSemestralDate(),
                    config.getEvaluateTeacherDate()
            );

            //Update current periodNumber
            int triNumber = config.getTrimestralNumber();

            triNumber++;
            if(triNumber > 4) triNumber = 1;

            this.schoolSettingsRepository.updateTrimestralNumber(triNumber);
        }

        if(semDate.equals(formattedDate)){
            var split = formattedDate.split("/");
            var month = Integer.parseInt(split[1]);
            month += 6;

            if(month > 12) month = 1;

            split[1] = Integer.toString(month);
            schoolPeriods newPeriod = new schoolPeriods(6,String.join("/",split),formattedDate);
            schoolPeriods save = this.schoolPeriodRepository.save(newPeriod);
            this.schoolSettingsRepository.updateCurrentSemestre(save.getPeriodID());
            this.schoolSettingsRepository.updateDateSettings(
                    config.getReportCardActivateBimestralDate(),
                    config.getReportCardActivateTrimstralDate(),
                    String.join("/",split),
                    config.getEvaluateTeacherDate()
            );

            //Update current periodNumber
            int semNumber = config.getTrimestralNumber();

            semNumber++;
            if(semNumber > 4) semNumber = 1;

            this.schoolSettingsRepository.updateSemestralNumber(semNumber);
        }
    }
}
