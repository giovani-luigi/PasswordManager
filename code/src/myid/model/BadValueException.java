package myid.model;

public class BadValueException extends Exception {
    public final String message;
    public BadValueException(String message) {
        this.message = message;
    }
}