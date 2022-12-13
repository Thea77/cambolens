package co.istad.cambolens.api.email.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class EmailDto<T> {

    @NotBlank
    private String receiver;

    @NotBlank
    private String subject;

    @JsonIgnore
    private String templateName;

    private T additionalInfo;

}
