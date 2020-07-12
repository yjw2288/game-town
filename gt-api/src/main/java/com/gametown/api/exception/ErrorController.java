package com.gametown.api.exception;

import com.gametown.exception.GameTownException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorController {
    @ResponseBody
    @ExceptionHandler(value = GameTownException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionView handleGameTownException(GameTownException gte) {
        ExceptionView view = new ExceptionView();
        view.setCode(gte.code);
        view.setMessage(gte.message);
        return view;
    }
}
