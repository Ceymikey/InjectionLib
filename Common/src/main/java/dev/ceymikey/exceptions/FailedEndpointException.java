package dev.ceymikey.exceptions;

public class FailedEndpointException extends Exception {

    public FailedEndpointException() {
        super("An issue occurred while finding endpoint : the embed url was either left empty or was invalid!");
    }
}
