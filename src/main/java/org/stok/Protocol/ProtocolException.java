package org.stok.Protocol;

public class ProtocolException extends Exception {
    private final ResponseCodes code;

    public ProtocolException(ResponseCodes code, String message) {
        super(message);
        this.code = code;
    }

    public ResponseCodes getCode(){
        return this.code;
    }
}
