package org.stok.protocol.response;

import org.stok.protocol.ResponseCodes;

public class Response {
    private ResponseCodes statusCode;
    private String message;
    private Object data;

    public static Response error(ResponseCodes statusCode, String errorMessage) {
        Response res = new Response();
        res.setStatusCode(statusCode);
        res.setMessage(errorMessage);
        return res;
    }

    private ResponseCodes getStatusCode() {
        return statusCode;
    }

    private void setStatusCode(ResponseCodes statusCode) {
        this.statusCode = statusCode;
    }

    private String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    private Object getData() {
        return data;
    }

    private void setData(ResponseData data) {
        this.data = data;
    }
}
