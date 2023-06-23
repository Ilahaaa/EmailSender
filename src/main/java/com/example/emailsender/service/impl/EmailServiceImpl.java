package com.example.emailsender.service.impl;
import com.example.emailsender.entity.EmailDetails;
import com.example.emailsender.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.io.IOException;
import java.util.Objects;

@Service
public class EmailServiceImpl implements EmailService {


    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Value("${spring.mail.username}")
    private String sender;


    @Override
    public void sendSimpleMail(EmailDetails details) {


            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();
            System.out.println(sender);
            mailMessage.setFrom(sender);
            String recipient= Objects.requireNonNull(details.getRecipient(),"Recipient's mail must not be null");
            mailMessage.setTo(recipient);
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());
            javaMailSender.send(mailMessage);



    }


    @Override
    public void sendMailWithAttachment(MultipartFile[] file, EmailDetails details) throws MessagingException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(sender);
        String recipient= Objects.requireNonNull(details.getRecipient(),"Recipient's mail must not be null");
        helper.setTo(recipient);
        helper.setSubject(details.getSubject());
        helper.setText(details.getMsgBody());
        for (MultipartFile multipartFile : file) {
            helper.addAttachment(multipartFile.getOriginalFilename(), new ByteArrayResource(multipartFile.getBytes()));

        }
//        for (int i = 0; i < file.length ; i++) {
//            helper.addAttachment(file[i].getOriginalFilename(),new ByteArrayResource(file[i].getBytes()));
//        }
        javaMailSender.send(message);

    }




    public void sendMail(EmailDetails details) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        Context thymeleafContext = new Context();
        thymeleafContext.setVariable("name", details.getName());
        thymeleafContext.setVariable("email", details.getRecipient());

        String emailContent = templateEngine.process("email", thymeleafContext);
        helper.setFrom(sender);
        helper.setTo(details.getRecipient());
        helper.setSubject(details.getSubject());
        helper.setText(emailContent, true);
        javaMailSender.send(message);
    }


}