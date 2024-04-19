package com.anish.emailservice.services.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.anish.emailservice.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender mailSender;

    private Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String to, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setFrom("sendmailtestanish@gmail.com");
        mailSender.send(simpleMailMessage);
        logger.info("Email sent successfully to " + to);    }

    @Override
    public void sendEmail(String[] to, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setFrom("sendmailtestanish@gmail.com");
        mailSender.send(simpleMailMessage);
    }

    @Override
    public void sendEmailWithHtml(String to, String subject, String htmlContent) {
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        MimeMessage simpleMailMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(simpleMailMessage, true, "UTF-8");
            mimeMessageHelper.setFrom("sendmailtestanish@gmail.com");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(htmlContent, true);
            mailSender.send(simpleMailMessage);
            logger.info("Email sent successfully to " + to);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendEmailWithAttachment(String to, String subject, String message, File file) {
        MimeMessage mimeMessage=mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom("sendmailtestanish@gmail.com");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(message);
            FileSystemResource fileSystemResource= new FileSystemResource(file);
            mimeMessageHelper.addAttachment(Objects.requireNonNull(fileSystemResource.getFilename()), file);
            mailSender.send(mimeMessage);
            logger.info("Email sent successfully to " + to + " with attachment");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendEmailWithAttachment(String to, String subject, String message, InputStream is) {
        MimeMessage mimeMessage=mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom("sendmailtestanish@gmail.com");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setText(message, true);
            mimeMessageHelper.setSubject(subject);

//            attachment for input file
            File file = new File("/Users/anishmandal/Downloads/projects/spring/email-project/emailservice/src/main/resources/static/assign.png");
            Files.copy(is, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            FileSystemResource fileSystemResource = new FileSystemResource(file);
            mimeMessageHelper.addAttachment(Objects.requireNonNull(fileSystemResource.getFilename()), fileSystemResource);

            mailSender.send(mimeMessage);
            logger.info("Email sent successfully to " + to + " with attachment");
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
