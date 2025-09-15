package com.github.cleytonorocha.vue_dashboard_back.exception;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException() {
        super("Item not found in the database. Please check the ID and try again.");
    }
}
