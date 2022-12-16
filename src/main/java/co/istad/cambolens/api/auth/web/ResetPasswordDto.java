package co.istad.cambolens.api.auth.web;

import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import co.istad.cambolens.shared.validation.password.Password;
import co.istad.cambolens.shared.validation.password.PasswordMatch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@PasswordMatch(password = "newPassword", confirmedPassword = "confirmedPassword")
public class ResetPasswordDto {
    
    // private static final int EXPIRATION = 60 * 24;

    // private Date expiryDate;
    
    // @Email
    // @NotBlank
    // private String email;

    @Password
    @NotBlank
    private String newPassword;

    @Password
    @NotBlank
    private String confirmedPassword;

    private String token;
}
