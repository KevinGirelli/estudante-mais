package com.project.EstudanteMais.Entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class schoolPeriods {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID periodID;

    @Column(name = "initPeriodDate")
    String initPeriodDate;

    @Column(name = "endPeriodDate")
    String endPeriodDate;

    @Column(name = "periodType")
    int periodType;

    public schoolPeriods(){}

    public schoolPeriods(int periodType, String endPeriodDate, String initPeriodDate) {
        this.periodType = periodType;
        this.endPeriodDate = endPeriodDate;
        this.initPeriodDate = initPeriodDate;
    }

    public UUID getPeriodID() {
        return periodID;
    }

    public void setPeriodID(UUID periodID) {
        this.periodID = periodID;
    }

    public String getInitPeriodDate() {
        return initPeriodDate;
    }

    public void setInitPeriodDate(String initPeriodDate) {
        this.initPeriodDate = initPeriodDate;
    }

    public String getEndPeriodDate() {
        return endPeriodDate;
    }

    public void setEndPeriodDate(String endPeriodDate) {
        this.endPeriodDate = endPeriodDate;
    }

    public int getPeriodType() {
        return periodType;
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }
}
