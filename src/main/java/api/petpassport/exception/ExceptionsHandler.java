package api.petpassport.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExceptionDetail {

        @JsonProperty(value = "timestamp")
        private Instant timestamp;

        @JsonProperty(value = "status")
        private HttpStatus status;

        @JsonProperty(value = "message")
        private String message;

        @JsonProperty(value = "details")
        private String details;

        @JsonProperty(value = "code")
        private int code;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request
    ) {
        List<String> messages = new ArrayList<>();

        int numberOfErrorMessage = 1;
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            if (e.getBindingResult().getFieldErrors().size() > 1) {
                messages.add(String.format(
                        "Wrong request param #%s - %s",
                        numberOfErrorMessage,
                        error.getDefaultMessage())
                );
            } else {
                messages.add(error.getDefaultMessage());
            }
            numberOfErrorMessage++;
        }

        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST)
                .message(String.join("<br />", messages))
                .details(request.getDescription(true))
                .build();

        return handleExceptionInternal(e, exceptionDetail, headers, exceptionDetail.getStatus(), request);
    }

    @ExceptionHandler({
            ConstraintViolationException.class
    })
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException e, WebRequest request) {
        List<String> messages = new ArrayList<>();

        int numberOfErrorMessage = 1;
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            messages.add(String.format(
                    "Wrong request param #%s - %s",
                    numberOfErrorMessage,
                    violation.getMessage())
            );
            numberOfErrorMessage++;
        }

        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST)
                .message(String.join("<br />", messages))
                .details(request.getDescription(true))
                .build();

        return handleExceptionInternal(e, exceptionDetail, new HttpHeaders(), exceptionDetail.getStatus(), request);
    }

    @ExceptionHandler({
            SQLException.class
    })
    public ResponseEntity<Object> handleSqlException(MethodArgumentTypeMismatchException e,
                                                     WebRequest request) {

        String message = "The wrong request params - please check data";

        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(message)
                .details(request.getDescription(true))
                .build();

        return handleExceptionInternal(e, exceptionDetail, new HttpHeaders(), exceptionDetail.getStatus(), request);
    }

    @ExceptionHandler(ConflictException.class)
    public final ResponseEntity<Object> handleConflictException(ConflictException e, WebRequest request) {
        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.CONFLICT)
                .message(e.getMessage())
                .details(request.getDescription(true))
                .build();

        return handleExceptionInternal(e, exceptionDetail, new HttpHeaders(), exceptionDetail.getStatus(), request);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleNotFoundException(NotFoundException e, WebRequest request) {
        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.NOT_FOUND)
                .message(e.getMessage())
                .details(request.getDescription(true))
                .build();

        return handleExceptionInternal(e, exceptionDetail, new HttpHeaders(), exceptionDetail.getStatus(), request);
    }

    @ExceptionHandler(ForbiddenException.class)
    public final ResponseEntity<Object> handleForbiddenException(ForbiddenException e, WebRequest request) {
        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.FORBIDDEN)
                .message(e.getMessage())
                .details(request.getDescription(true))
                .build();

        return handleExceptionInternal(e, exceptionDetail, new HttpHeaders(), exceptionDetail.getStatus(), request);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public final ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException e, WebRequest request) {
        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.UNAUTHORIZED)
                .message(e.getMessage())
                .details(request.getDescription(true))
                .build();

        return handleExceptionInternal(e, exceptionDetail, new HttpHeaders(), exceptionDetail.getStatus(), request);
    }

    @ExceptionHandler(SomethingWentWrongException.class)
    public final ResponseEntity<Object> handleSomethingWentWrongException(SomethingWentWrongException e, WebRequest request) {
        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(e.getMessage())
                .details(request.getDescription(true))
                .build();

        return handleExceptionInternal(e, exceptionDetail, new HttpHeaders(), exceptionDetail.getStatus(), request);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public final ResponseEntity<Object> handleJsonProcessingException(JsonProcessingException e, WebRequest request) {
        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(e.getMessage())
                .details(request.getDescription(true))
                .build();

        return handleExceptionInternal(e, exceptionDetail, new HttpHeaders(), exceptionDetail.getStatus(), request);
    }
}
