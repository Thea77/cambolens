package co.istad.cambolens.shared.validation.email;

import co.istad.cambolens.api.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class ConstraintEmailValidator implements ConstraintValidator<ConstraintEmail, String> {

    private final UserServiceImpl userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !userService.checkUserEmail(email);
    }

}
