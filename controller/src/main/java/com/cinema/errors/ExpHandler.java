package com.cinema.errors;
import com.gigaspaces.client.ChangeException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class ExpHandler {
	
    @ResponseStatus(HttpStatus.NOT_FOUND) 
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public Errors notExist() {
    	Errors errors = new Errors();
    	errors.setMessage("Resource is no exist");
        return errors;
    }


}
