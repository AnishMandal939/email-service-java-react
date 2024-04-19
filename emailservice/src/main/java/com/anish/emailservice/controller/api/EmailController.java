package com.anish.emailservice.controller.api;

import com.anish.emailservice.helper.CustomResponse;
import com.anish.emailservice.helper.EmailRequest;
import com.anish.emailservice.services.EmailService;
import jakarta.mail.Multipart;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/api/v1/email")
@RestController
@CrossOrigin("*")
public class EmailController {
    private EmailService emailService;

    public EmailController(EmailService emailService){
        this.emailService = emailService;
    }
//    send email
    @PostMapping("/send")
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest request){
        emailService.sendEmailWithHtml(request.getTo(), request.getSubject(), request.getMessage());
        return ResponseEntity.ok(
                CustomResponse.builder().message("Email sent successfully").success(true).httpStatus(HttpStatus.OK).success(true).build()
        );
    }

    @PostMapping("/send-with-file")
    public  ResponseEntity<CustomResponse> sendWithFile(@RequestPart EmailRequest request, @RequestPart MultipartFile file) throws IOException {
        emailService.sendEmailWithAttachment(request.getTo(), request.getSubject(), request.getMessage(), file.getInputStream());
        return ResponseEntity.ok(
                CustomResponse.builder().message("Email sent successfully with file").success(true).httpStatus(HttpStatus.OK).success(true).build()
        );
    }
}
