package ed.uopp.uoppcore.exception;

import ed.uopp.uoppcore.security.data.HttpErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;

@ControllerAdvice
public class DefaultExceptionHandler {

    private static ResponseEntity<HttpErrorResponse> buildResponseEntity(HttpStatus status, String message) {
        HttpErrorResponse response = new HttpErrorResponse(status.value(), status, status.getReasonPhrase().toUpperCase(), message);
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<HttpErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<HttpErrorResponse> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<HttpErrorResponse> handleDuplicateException(DuplicateException ex, WebRequest request) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

}
