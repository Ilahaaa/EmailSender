package com.example.emailsender.service;


import com.example.emailsender.entity.EmailDetails;


public interface EmailService {

    String sendSimpleMail(EmailDetails details);


    String sendMailWithAttachment(EmailDetails details);
}