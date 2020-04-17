package com.my.notes.mails.services;

import com.my.notes.mails.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class MailServices {

    private final JavaMailSender mailSender;

    @Autowired
    public MailServices(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public boolean sendEmail(Email email, boolean isHtmlContentFormat) {
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(email.getTo());
            messageHelper.setFrom(email.getFrom());
            messageHelper.setSubject(email.getSubject());
            messageHelper.setText(
                    isHtmlContentFormat ? new String(Base64.getDecoder().decode(email.getContent())) : email.getContent(),
                    isHtmlContentFormat
            );
        };

        // Decommenter pour vraiment envoyer des emails.
        //mailSender.send(mimeMessagePreparator);
        return true;
    }

}
