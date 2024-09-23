package com.project.EstudanteMais.Entity;

public enum periodType {
    Matutino("Matutino"),
    Vespertino("Vespertino"),
    Integral("Integral"),
    Noturno("Noturno"),
    Matutino_Noturno("Matutino + Noturno"),
    Vespertino_Noturno("Vespertino + Noturno"),
    Integral_Noturno("Integral + Noturno");

    private String type;

    periodType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
