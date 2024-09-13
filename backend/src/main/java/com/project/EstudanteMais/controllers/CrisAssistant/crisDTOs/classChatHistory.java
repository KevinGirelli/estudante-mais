package com.project.EstudanteMais.controllers.CrisAssistant.crisDTOs;

import java.util.List;

public class classChatHistory {
  String nome_da_turma;
  String professor;
  List<messagedto> historico_de_mensagem;

  public String getNome_da_turma() {
    return nome_da_turma;
  }

  public void setNome_da_turma(String nome_da_turma) {
    this.nome_da_turma = nome_da_turma;
  }

  public String getProfessor() {
    return professor;
  }

  public void setProfessor(String professor) {
    this.professor = professor;
  }

  public List<messagedto> getHistorico_de_mensagem() {
    return historico_de_mensagem;
  }

  public void setHistorico_de_mensagem(List<messagedto> historico_de_mensagem) {
    this.historico_de_mensagem = historico_de_mensagem;
  }
}
