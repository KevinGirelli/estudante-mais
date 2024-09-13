package com.project.EstudanteMais.controllers.CrisAssistant.crisDTOs;

public class assessmentsDTO {
  String nome_da_prova;
  String data_da_prova;
  String professor;
  String disciplina;

  String nota_da_prova;

  public String getNome_da_prova() {
    return nome_da_prova;
  }

  public void setNome_da_prova(String nome_da_prova) {
    this.nome_da_prova = nome_da_prova;
  }

  public String getData_da_prova() {
    return data_da_prova;
  }

  public void setData_da_prova(String data_da_prova) {
    this.data_da_prova = data_da_prova;
  }

  public String getProfessor() {
    return professor;
  }

  public void setProfessor(String professor) {
    this.professor = professor;
  }

  public String getDisciplina() {
    return disciplina;
  }

  public void setDisciplina(String disciplina) {
    this.disciplina = disciplina;
  }

  public String getNota_da_prova() {
    return nota_da_prova;
  }

  public void setNota_da_prova(String nota_da_prova) {
    this.nota_da_prova = nota_da_prova;
  }
}
