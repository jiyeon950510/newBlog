package shop.mtcoding.newblog.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import shop.mtcoding.newblog.dto.ResponseDto;
import shop.mtcoding.newblog.handler.ex.CustomApiException;
import shop.mtcoding.newblog.handler.ex.CustomException;
import shop.mtcoding.newblog.util.Script;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public String CustomException(CustomException e) {
        return Script.back(e.getMessage());
    }

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> CustomApiException(CustomApiException e) {
        return new ResponseEntity<>(Script.back(e.getMessage()), e.getStatus());
    }
}
