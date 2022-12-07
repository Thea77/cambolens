package co.istad.cambolens.shared.rest;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestError<T> {

    private Boolean status;
    private Integer code;
    private String message;
    private Timestamp timestamp;
    private T error;

}
