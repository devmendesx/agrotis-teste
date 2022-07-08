package com.agrotis.labor.config;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class ErrorsHandler {

    List<String> errors;

    public ErrorsHandler (List<String> errors_){
        errors = errors_;
    }
    public ErrorsHandler(String error_){
        errors = List.of(error_);
    }

}
