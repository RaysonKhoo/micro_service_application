package org.example.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
@RestControllerAdvice
public class CustomExceptionHandle {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<CustomError> ProductNotFoundException(ProductNotFoundException ex) {
        CustomError error = new CustomError(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(ProductNotFoundException.class)
//    public Map<String, String> ProductNotFoundException(ProductNotFoundException ex){
//        Map<String, String> error = new HashMap<>();
//        error.put("error :", ex.getMessage());
//        return error;
//    }
}
