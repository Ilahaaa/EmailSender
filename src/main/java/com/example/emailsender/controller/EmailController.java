package com.example.emailsender.controller;

import com.example.emailsender.entity.EmailDetails;
import com.example.emailsender.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


@RestController
public class EmailController {


    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }


    @PostMapping("/sendMail")
    public ResponseEntity<String>
    sendMail(@RequestBody EmailDetails details) {
        emailService.sendSimpleMail(details);
        return new ResponseEntity<>("Mail sent successfully", HttpStatus.CREATED);
    }


    // Sending email with attachment
    @PostMapping("/sendMailWithAttachment")
    public ResponseEntity<String> send(
            @RequestParam(value = "file",required = false) MultipartFile[] file,EmailDetails details
    ) throws MessagingException, IOException {
        emailService.sendMailWithAttachment(file,details);
        return new ResponseEntity<>("Mail sent successfully", HttpStatus.CREATED);
    }



    @PostMapping("/sendMailWithHtml")
    public ResponseEntity<String>
    sendMailWithHtmlTable(@RequestBody EmailDetails details) throws MessagingException {
        emailService.sendMail(details);
        return new ResponseEntity<>("Mail sent successfully", HttpStatus.CREATED);
    }


}