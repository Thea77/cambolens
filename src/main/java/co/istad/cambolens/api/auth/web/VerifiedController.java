package co.istad.cambolens.api.auth.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
public class VerifiedController {

    @GetMapping("verified-successfully")
    ModelAndView verifyEmail() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("email/email-confirmation-succeed");

        return modelAndView;
    }

}
