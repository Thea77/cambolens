package co.istad.cambolens.api.user;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class Role implements GrantedAuthority{
    private Integer id;
    private String name;

    @Override
    public String getAuthority() {
        //all roles must prefix with 'ROLE_'
        return "ROLE_"+name;
    }
}
