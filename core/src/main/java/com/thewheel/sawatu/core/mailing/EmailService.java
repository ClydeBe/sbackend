package com.thewheel.sawatu.core.mailing;

import com.thewheel.sawatu.shared.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender sender;
    private final SpringTemplateEngine thymeleafTemplateEngine;

    @Value("${spring.mail.username}")
    private String from;

    private void sendEmail(String subject,
                           String message,
                           String... to) {

        MimeMessage msg = sender.createMimeMessage();
        MimeMessageHelper mail = null;
        try {
            mail = new MimeMessageHelper(msg, true, "UTF-8");
            mail.setFrom(from);
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(message, true);
            sender.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    public void sendActivationMessage(UserDto user, String token, String... to) {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("token", token);
        String message =
                thymeleafTemplateEngine.process("validationToken.html", context);
        sendEmail("Bienvenu(e) Chez Sawatu! Activation de compte",
                message,
                to);
    }

    public void sendWelcomeMessage(String username, String... to) {
        Context context = new Context();
        context.setVariable("username", username);
        String message =
                thymeleafTemplateEngine.process("welcome.html", context);
        sendEmail("Bienvenu(e) Chez Sawatu",
                message,
                to);
    }
}
