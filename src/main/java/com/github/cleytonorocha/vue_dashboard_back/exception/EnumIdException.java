package com.github.cleytonorocha.vue_dashboard_back.exception;

public class EnumIdException extends RuntimeException {
    public EnumIdException() {
        super("Invalid Enum ID provided.");
    }
}
