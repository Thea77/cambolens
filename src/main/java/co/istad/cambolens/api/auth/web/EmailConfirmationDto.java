package co.istad.cambolens.api.auth.web;

// import co.istad.cambolens.api.shared.validation.email.ConstraintEmail;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class EmailConfirmationDto {

    @Email
    @NotBlank
    @JsonProperty("email")
    private String value;

}
