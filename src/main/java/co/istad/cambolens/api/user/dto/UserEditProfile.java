package co.istad.cambolens.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserEditProfile {
    private String email;
    private String familyName;
    private String givenName;
    private String phoneNumber;
}
