package com.project.EstudanteMais.controllers.CrisAssistant.crisDTOs;

public class messagedto {
  String remetente;
  String mensagem;
  String data_de_envio_mensagem;

  public String getRemetente() {
    return remetente;
  }

  public void setRemetente(String remetente) {
    this.remetente = remetente;
  }

  public String getMensagem() {
    return mensagem;
  }

  public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
  }

  public String getData_de_envio_mensagem() {
    return data_de_envio_mensagem;
  }

  public void setData_de_envio_mensagem(String data_de_envio_mensagem) {
    this.data_de_envio_mensagem = data_de_envio_mensagem;
  }
}
