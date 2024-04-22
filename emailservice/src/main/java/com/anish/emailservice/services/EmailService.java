package com.anish.emailservice.services;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.anish.emailservice.helper.Message;

public interface EmailService{

    void sendEmail(String to, String subject, String message);
    //    send email to multiple person- overloaded method
    void sendEmail(String[] to, String subject, String message);
    //    sendemailwithhtml
    void sendEmailWithHtml(String to, String subject, String htmlContent);
    //    send email with file

    void sendEmailWithAttachment(String to, String subject, String message, File file);

    void sendEmailWithAttachment(String to, String subject, String message, InputStream is);

    List<Message> getInboxMessages();
}