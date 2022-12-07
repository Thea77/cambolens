package co.istad.cambolens.api.user.dto;

import java.util.List;

import co.istad.cambolens.api.file.dto.FileDto;
import co.istad.cambolens.api.user.Role;
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
public class UserDto {
    private String username;
    private String email;
    private String familyName;
    private String givenName;
    private String phoneNumber;
    private FileDto profile;

}
