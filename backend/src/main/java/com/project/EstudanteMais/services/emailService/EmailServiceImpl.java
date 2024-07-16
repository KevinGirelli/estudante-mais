package com.project.EstudanteMais.services.emailService;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {

  @Value("${spring.mail.verify.host}")
  private String host;

  @Value("${spring.mail.username}")
  private String fromEmail;

  @Autowired
  private JavaMailSender emailSender;

  @Override
  public void sendSimpleMailMessage(String name, String to, String token,String text) {
    try{
      SimpleMailMessage message = new SimpleMailMessage();
      message.setSubject(name);
      message.setFrom(fromEmail);
      message.setTo(to);
      message.setText(text);
      emailSender.send(message);
    }catch (Exception erro){
      System.out.println(erro.getMessage());
      throw new RuntimeException(erro.getMessage());
    }
  }

  @Override
  public void sendMimeMessageWithAttachments(String name, String to, String token) {

  }

  @Override
  public void sendMimeMessageWithEmbbedImages(String name, String to, String token) {
    MimeMessage message = emailSender.createMimeMessage();
    try{
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setSubject(name);
      helper.setTo(to);
      helper.setFrom(fromEmail);
      helper.setText("Codigo", true);


      ClassPathResource file = new ClassPathResource("static/emailCode.png");
      helper.addAttachment("emailCode.png", file);

      emailSender.send(message);
    }catch (Exception erro){
      System.out.println(erro);
    }
  }

  @Override
  public void sendMimeMessageWithEmbbedFiles(String name, String to, String token) {

  }

  @Override
  public void sendHtmlEmail(String name, String to, String htmlContent) {
    MimeMessage message = emailSender.createMimeMessage();

    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setFrom(fromEmail);
      helper.setTo(to);
      helper.setSubject(name);
      helper.setText(htmlContent, true); // true indica que o conteúdo é HTML

      emailSender.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
      System.out.println("Erro ao enviar email: " + e.getMessage());
    }
  }

  @Override
  public void sendHtmlEmailWithEmbbedFiles(String name, String to, String token) {

  }
}
