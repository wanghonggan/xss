package com.example.demo.databind;

public class FormConvertValidatedException extends RuntimeException {

    private String name;

    public FormConvertValidatedException(String name, String message) {
        super(message);
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
