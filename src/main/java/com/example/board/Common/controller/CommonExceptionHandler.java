package com.example.board.Common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public String entityNotfound(EntityNotFoundException e, Model model){
        model.addAttribute("errorMessage",e.getMessage());
        log.error(e.getMessage());
        return "common/error_page";
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public String illegalArgument(IllegalArgumentException e, Model model){
        model.addAttribute("errorMessage",e.getMessage());

        return "common/error_page";
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String validException(MethodArgumentNotValidException e, Model model){
        model.addAttribute("errorMessage",e.getMessage());

        return "common/error_page";
    }
}
