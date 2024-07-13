package com.project.EstudanteMais.services.emailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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

  }

  @Override
  public void sendMimeMessageWithEmbbedFiles(String name, String to, String token) {

  }

  @Override
  public void sendHtmlEmail(String name, String to, String token) {

  }

  @Override
  public void sendHtmlEmailWithEmbbedFiles(String name, String to, String token) {

  }
}
