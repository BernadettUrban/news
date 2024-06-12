package com.mjc.school.exceptions;

public class PaginationException extends RuntimeException{
    public PaginationException(String message) {
        super(message);
    }
}
