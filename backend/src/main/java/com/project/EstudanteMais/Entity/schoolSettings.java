package com.project.EstudanteMais.Entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class schoolSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID configID;

    @Column(name = "reportCardActiveBimestralDate")
    String reportCardActivateBimestralDate;

    @Column(name = "reportCardActiveTrimestralDate")
    String reportCardActivateTrimstralDate;

    @Column(name = "reportCardActiveSemestralDate")
    String reportCardActivateSemestralDate;

    @Column(name = "bimestralNumber")
    int bimestralNumber;

    @Column(name = "trimestralNumber")
    int trimestralNumber;

    @Column(name = "semestralNumber")
    int semestralNumber;

    @ManyToOne
    schoolPeriods currentBi;

    @ManyToOne
    schoolPeriods currentTri;

    @ManyToOne
    schoolPeriods currentSem;

    @Column(name = "periodType",nullable = false)
    periodType period;

    public periodType getPeriod() {
        return period;
    }

    public void setPeriod(periodType period) {
        this.period = period;
    }

    public int getBimestralNumber() {
        return bimestralNumber;
    }

    public void setBimestralNumber(int bimestralNumber) {
        this.bimestralNumber = bimestralNumber;
    }

    public int getTrimestralNumber() {
        return trimestralNumber;
    }

    public void setTrimestralNumber(int trimestralNumber) {
        this.trimestralNumber = trimestralNumber;
    }

    public int getSemestralNumber() {
        return semestralNumber;
    }

    public void setSemestralNumber(int semestralNumber) {
        this.semestralNumber = semestralNumber;
    }

    public UUID getConfigID() {
        return configID;
    }

    public void setConfigID(UUID configID) {
        this.configID = configID;
    }

    public schoolPeriods getCurrentBi() {
        return currentBi;
    }

    public void setCurrentBi(schoolPeriods currentBi) {
        this.currentBi = currentBi;
    }

    public schoolPeriods getCurrentTri() {
        return currentTri;
    }

    public void setCurrentTri(schoolPeriods currentTri) {
        this.currentTri = currentTri;
    }

    public schoolPeriods getCurrentSem() {
        return currentSem;
    }

    public void setCurrentSem(schoolPeriods currentSem) {
        this.currentSem = currentSem;
    }

    public String getReportCardActivateBimestralDate() {
        return reportCardActivateBimestralDate;
    }

    public void setReportCardActivateBimestralDate(String reportCardActivateBimestralDate) {
        this.reportCardActivateBimestralDate = reportCardActivateBimestralDate;
    }

    public String getReportCardActivateTrimstralDate() {
        return reportCardActivateTrimstralDate;
    }

    public void setReportCardActivateTrimstralDate(String reportCardActivateTrimstralDate) {
        this.reportCardActivateTrimstralDate = reportCardActivateTrimstralDate;
    }

    public String getReportCardActivateSemestralDate() {
        return reportCardActivateSemestralDate;
    }

    public void setReportCardActivateSemestralDate(String reportCardActivateSemestralDate) {
        this.reportCardActivateSemestralDate = reportCardActivateSemestralDate;
    }
}
