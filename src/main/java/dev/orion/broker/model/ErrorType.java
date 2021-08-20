package dev.orion.broker.model;

public class ErrorType {
    public Integer code;
    public String message;

    public ErrorType() {
    }

    public ErrorType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
