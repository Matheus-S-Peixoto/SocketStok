package org.stok.Protocol;

public enum ResponseCodes {
    OK(200),
    CREATED(201),
    BAD_REQUEST(400),
    NOT_FOUND(404),
    INTERNAL_ERROR(500);

    private final int code;

    ResponseCodes(int code) {
        this.code = code;
    }


    public int getCode() {
        return code;
    }
}

