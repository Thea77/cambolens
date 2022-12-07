package co.istad.cambolens.shared.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ValidatedError {
    
    private String field;
    private String message;

}
