package co.istad.cambolens.api.user;

import java.util.List;

import co.istad.cambolens.api.file.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private String familyName;
    private String givenName;
    private String phoneNumber;
    private File profile;
    private Boolean isEnabled;
    private String verificationCode;
    private List<Role> userRoles;
}
