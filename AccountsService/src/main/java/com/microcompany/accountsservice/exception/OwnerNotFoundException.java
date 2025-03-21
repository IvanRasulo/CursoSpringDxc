package com.microcompany.accountsservice.exception;

public class OwnerNotFoundException extends RuntimeException {
    protected static final long serialVersionUID = 2L;

    public OwnerNotFoundException(String message) {
        super("Owner no encontrado");
    }

    public OwnerNotFoundException(Long ownerId) {
        super("Owner with id: " + ownerId + " not found");
    }
}
