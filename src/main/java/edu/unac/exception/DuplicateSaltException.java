package edu.unac.exception;

public class DuplicateSaltException extends Exception {
    public DuplicateSaltException(String message) {
        super(message);
    }
}
