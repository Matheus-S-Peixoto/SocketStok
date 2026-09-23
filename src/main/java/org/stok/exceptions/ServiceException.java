package org.stok.exceptions;

public class ServiceException extends Exception {
    private final ResponseCodes code;

    public ServiceException(ResponseCodes code, String message) {
        super(message);
        this.code = code;
    }

    public ResponseCodes getCode() {
        return this.code;
    }
}
