package io.github.alberes.guestsjpads.controllers.exception;

import io.github.alberes.guestsjpads.controllers.dto.FieldErrorDto;
import io.github.alberes.guestsjpads.services.exception.DuplicateRecordException;
import io.github.alberes.guestsjpads.services.exception.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFoundException(
            ObjectNotFoundException objectNotFoundException,
            HttpServletRequest httpServletRequest
    ){
        StandardError standardError = new StandardError(
                System.currentTimeMillis(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                objectNotFoundException.getMessage(),
                httpServletRequest.getRequestURI(),
                List.of()
        );
        log.error(standardError.toString());
        return ResponseEntity.status(standardError.getStatus()).body(standardError);
    }

    @ExceptionHandler(DuplicateRecordException.class)
    public ResponseEntity<StandardError> DuplicateRecordException(
            DuplicateRecordException duplicateRecordException,
            HttpServletRequest httpServletRequest
    ){
        StandardError standardError = new StandardError(
            System.currentTimeMillis(),
            HttpStatus.CONFLICT.value(),
            "Conflit",
            duplicateRecordException.getMessage(),
            httpServletRequest.getRequestURI(),
            List.of()
        );
        log.error(standardError.toString());
        return ResponseEntity.status(standardError.getStatus()).body(standardError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> methodArgumentNotValidException(
            MethodArgumentNotValidException methodArgumentNotValidException,
            HttpServletRequest httpServletRequest){
        List<FieldError> fieldErrors = methodArgumentNotValidException.getFieldErrors();

        List<FieldErrorDto> errors = fieldErrors
                .stream()
                .map(fe -> new FieldErrorDto(
                        fe.getField(), fe.getDefaultMessage())
                )
                .toList();

        StandardError standardError = new StandardError(
                System.currentTimeMillis(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                methodArgumentNotValidException.getMessage(),
                httpServletRequest.getRequestURI(),
                errors
        );
        log.error(standardError.toString());
        return ResponseEntity.status(standardError.getStatus()).body(standardError);
    }

}
