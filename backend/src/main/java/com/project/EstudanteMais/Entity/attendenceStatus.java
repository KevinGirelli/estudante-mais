package com.project.EstudanteMais.Entity;

public enum attendenceStatus {
  PRESENTE("PRESENTE"),
  JUSTIFICADO("JUSTIFICADO"),
  AUSENTE("AUSENTE");

  private String status;

  attendenceStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
