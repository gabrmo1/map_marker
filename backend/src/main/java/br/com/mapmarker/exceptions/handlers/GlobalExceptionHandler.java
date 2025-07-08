package br.com.mapmarker.exceptions.handlers;

import br.com.mapmarker.exceptions.CustomException;
import br.com.mapmarker.exceptions.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex, HttpServletRequest request) {
        final var resolvedMessage = messageSource.getMessage(ex.getMessageKey(), ex.getArgs(), LocaleContextHolder.getLocale());
        final var errorResponse = new ErrorResponse(ex.getStatus(), resolvedMessage, request.getRequestURI());

        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        final var errors = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).toList();
        final var locale = LocaleContextHolder.getLocale();
        final var mainMessage = messageSource.getMessage(errors.getFirst(), null, "Requisição inválida", locale);
        final var errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                mainMessage,
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest request) {
        final var locale = LocaleContextHolder.getLocale();
        final var errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, messageSource.getMessage("error.default", null, locale), request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
