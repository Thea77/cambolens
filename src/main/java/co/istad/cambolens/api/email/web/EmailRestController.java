package co.istad.cambolens.api.email.web;

import co.istad.cambolens.api.email.EmailServiceImpl;
import co.istad.cambolens.shared.rest.Rest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@ApiIgnore
@RestController
@RequestMapping("/api/v1/emails")
@RequiredArgsConstructor
public class EmailRestController {

    private final EmailServiceImpl emailService;

    @PostMapping
    ResponseEntity<?> doSendMail(@Valid @RequestBody EmailDto emailDto) throws MessagingException, UnsupportedEncodingException {

        var data = emailService.sendEmail(emailDto);

        Rest<?> rest = Rest.ok()
                .setMessage("Email has been sent.")
                .setData(data);

        return ResponseEntity.ok(rest);
    }

}
