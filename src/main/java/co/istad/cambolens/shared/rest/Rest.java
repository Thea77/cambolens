package co.istad.cambolens.shared.rest;

import java.sql.Timestamp;

import co.istad.cambolens.utils.DateTimeUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Accessors(chain = true)
public class Rest<T> {

    public Rest() {
        this.timestamp = DateTimeUtils.getTS();
    }
    
    private Boolean status;
    private Integer code;
    private String message;
    private Timestamp timestamp;
    private T data;

    public static <T> Rest<T> ok() {
        Rest<T> rest = new Rest<>();
        rest.setStatus(true);
        rest.setCode(HttpStatus.OK.value());
        return rest;
    }

    public static <T> Rest<T> forbidden() {
        Rest<T> rest = new Rest<>();
        rest.setData(null);
        rest.setMessage("Forbidden Access Denied");
        rest.setStatus(false);
        rest.setCode(HttpStatus.FORBIDDEN.value());
        return rest;
    }

}
