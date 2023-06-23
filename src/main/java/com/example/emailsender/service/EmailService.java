package com.example.emailsender.service;


import com.example.emailsender.entity.EmailDetails;
import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


public interface EmailService {

    void sendSimpleMail(EmailDetails details);

   void sendMailWithAttachment(MultipartFile[] file,EmailDetails details ) throws MessagingException, IOException;

    void sendMail(EmailDetails details) throws MessagingException;
}