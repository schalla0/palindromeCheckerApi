package com.example.palindrome.exception;

public class PalindromeServiceException extends RuntimeException{

    public PalindromeServiceException(String message) {
        super(message);
    }
    public PalindromeServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
