package co.istad.cambolens.api.email;

import co.istad.cambolens.api.email.web.EmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${api.base-url}")
    private String apiBaseUrl;


    @Override
    public EmailDto sendEmail(EmailDto<?> emailDto) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);

        var context = new Context();
        context.setVariable("additionalInfo", emailDto.getAdditionalInfo());
        context.setVariable("apiBaseUrl", apiBaseUrl);

        String html = templateEngine.process(emailDto.getTemplateName(), context);

        messageHelper.setTo(emailDto.getReceiver());
        messageHelper.setFrom(sender, appName);
        messageHelper.setText(html, true);
        messageHelper.setSubject(emailDto.getSubject());

        mailSender.send(message);

        return emailDto;
    }

}
