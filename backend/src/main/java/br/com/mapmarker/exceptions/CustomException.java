package br.com.mapmarker.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private final HttpStatus status;
    private final String messageKey;
    private final Object[] args;

    public CustomException(HttpStatus status, String messageKey, Object... args) {
        super(messageKey);

        this.status = status;
        this.messageKey = messageKey;
        this.args = args;
    }

}
