package hu.szaniszlaid.webdemo.controller;

public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
