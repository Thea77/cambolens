package co.istad.cambolens.api.email;

import co.istad.cambolens.api.email.web.EmailDto;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailService {

    EmailDto sendEmail(EmailDto<?> emailDto) throws MessagingException, UnsupportedEncodingException;

}
