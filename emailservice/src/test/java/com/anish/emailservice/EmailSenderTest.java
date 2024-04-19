package com.anish.emailservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.anish.emailservice.services.EmailService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest
public class EmailSenderTest {
    @Autowired
    private EmailService emailService;
    @Test
    void EmailSendTest(){
        System.out.println("sending mail!!");
        emailService.sendEmail("anish.mandal@bitskraft.com", "testing mail service", "testing successful!!");
    }
    @Test
    void sendHtmlInMail(){
        String html = ""+ "<h1>sending html in mail testing successful!!</h1>"+ "<h2 style='color: red;'>anish</h2>"+"";
        emailService.sendEmailWithHtml("anish.mandal@bitskraft.com", "testing mail service", html);
    }
    @Test
    void sendEmailWithAttachment(){
        emailService.sendEmailWithAttachment("anish.mandal@bitskraft.com", "testing mail service", "testing successful!!",
                new File("/Users/anishmandal/Downloads/projects/spring/email-project/emailservice/src/main/resources/static/assign.png"));
    }
    @Test
    void sendEmailWithInputStream(){
        File file = new File("/Users/anishmandal/Downloads/projects/spring/email-project/emailservice/src/main/resources/static/assign.png");
        try {
            InputStream is = new FileInputStream(file);
            emailService.sendEmailWithAttachment("anish.mandal@bitskraft.com", "testing mail service for file upload", "testing successful!!", is);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
