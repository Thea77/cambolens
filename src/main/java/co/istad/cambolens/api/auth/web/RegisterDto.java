package co.istad.cambolens.api.auth.web;

// import co.istad.cambolens.shared.validation.email.ConstraintEmail;
// import co.istad.cambolens.shared.validation.fileid.ConstraintFileId;
// import co.istad.cambolens.shared.validation.password.Password;
// import co.istad.cambolens.shared.validation.password.PasswordMatch;
// import co.istad.cambolens.shared.validation.username.ConstraintUsername;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import co.istad.cambolens.shared.constraint.fileid.ConstraintFileId;
import co.istad.cambolens.shared.validation.email.ConstraintEmail;
import co.istad.cambolens.shared.validation.password.Password;
import co.istad.cambolens.shared.validation.password.PasswordMatch;
import co.istad.cambolens.shared.validation.username.ConstraintUsername;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
@ToString
@PasswordMatch(password = "password", confirmedPassword = "confirmedPassword")
public class RegisterDto {

    @ConstraintUsername
    @NotBlank
    private String username;

    @ConstraintEmail
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String familyName;

    @NotBlank
    private String givenName;

    @NotBlank
    private String phoneNumber;

    @ConstraintFileId(message = "Profile ID does not exist!")
    private Long profileId;

    @Password
    @NotBlank
    private String password;

    @Password
    @NotBlank
    private String confirmedPassword;

    // private List<Integer> roleIds;
    private Integer roleIds;

}
