package hu.szaniszlaid.webdemo.controller;

import hu.szaniszlaid.webdemo.domain.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> handleConflictException(ConflictException exception) {

        ErrorDetail errorDetail = ErrorDetail.builder()
                .title("Entity conflict")
                .timeStamp(new Date().getTime())
                .detail(exception.getMessage())
                .developerMessage("Entity post must not contain id value. If you want to update an entity, use HTTP PUT or PATCH method.")
                .build();

        return new ResponseEntity<>(errorDetail, null, HttpStatus.CONFLICT);
    }


//
//    /**
//     * Catch RuntimeException and write log entry with request details
//     * */
//    @ExceptionHandler(value = RuntimeException.class)
//    protected void defaultExceptionHandler(HttpServletRequest req, Exception e){
//        log.error(req.getPathInfo() + " "+ req.getMethod(), e);
//    }
//
//    /**
//     * Catch Error and write log entry with request details
//     * */
//    @ExceptionHandler(value = Error.class)
//    protected void defaultErrorHandler(HttpServletRequest req, Exception e){
//        log.error(req.getPathInfo() + " "+ req.getMethod(), e);
//    }

}