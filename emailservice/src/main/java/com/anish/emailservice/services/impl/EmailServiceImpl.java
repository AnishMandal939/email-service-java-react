package com.anish.emailservice.services.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.anish.emailservice.helper.Message;
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

    @Value("${mail.store.protocol}")
    String protocol;
    @Value("${mail.imaps.host}")
    String host;
    @Value("${mail.imaps.port}")
    String port;
    @Value("${spring.mail.username}")
    String username;
    @Value("${spring.mail.password}")
    String password;


    @Override
    public List<Message> getInboxMessages() {
        // code to receive email: get all emails
        Properties configurations = new Properties();
        configurations.setProperty("mail.store.protocol", protocol);
        configurations.setProperty("mail.imaps.host", host);
        configurations.setProperty("mail.imaps.port", port);

        Session session = Session.getDefaultInstance(configurations);
        try {
            Store store = session.getStore();

            // authenticate
            store.connect(username, password);

            // get folder from store
            Folder inbox = store.getFolder("INBOX");

            inbox.open(Folder.READ_ONLY);
            jakarta.mail.Message[] messages = inbox.getMessages();

            // create a list of array for email
            List<Message> list = new ArrayList<>();


            for(jakarta.mail.Message message: messages){
//                System.out.println(message.getSubject());
                String content = getContentFromEmailMessage(message);
                List<String> files = getFilesFromEmailMessage(message);
//                System.out.println("--------");
                list.add(Message.builder().subjects(message.getSubject()).content(content).files(files).build());
            }
            return list;

        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
//        return null;
    }

    private List<String> getFilesFromEmailMessage(jakarta.mail.Message message) throws MessagingException, IOException {
        List<String> files = new ArrayList<>();
        if(message.isMimeType("multipart/*")){
            Multipart content = (Multipart) message.getContent();

            for(int i = 0; i< content.getCount(); i++){
                BodyPart bodyPart = content.getBodyPart(i);

                if(Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())){
                    InputStream inputStream =   bodyPart.getInputStream();
                    //   saved the file
                    File file = new File("src/main/resources/email/"+bodyPart.getFileName());

                    Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

                    //   urls
                    files.add(file.getAbsolutePath());
//                    return files;
                }
            }
        }
        return files;
    }

    private String getContentFromEmailMessage(jakarta.mail.Message message) throws MessagingException, IOException {
        if(message.isMimeType("text/plain") || message.isMimeType("text/html")){
            return (String) message.getContent();
        }else if(message.isMimeType("multipart/*")){
            Multipart part = (Multipart) message.getContent();

            for(int i =0; i< part.getCount(); i++){
                BodyPart bodyPart =  part.getBodyPart(i);
                if(bodyPart.isMimeType("text/plain")){
                    return (String) bodyPart.getContent();
                }
            }
        }
        return null;
    }

}
