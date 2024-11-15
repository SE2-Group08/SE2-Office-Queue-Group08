package it.polito.queuemanagementsystem.exception;

public class CounterNotFoundException extends RuntimeException {
    public CounterNotFoundException(String message) {
        super(message);
    }
}
