package org.stok.protocol.response;

import org.stok.exceptions.ResponseCodes;
import org.stok.protocol.Actions;

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

    public static Response success(Actions action, Object resData) {
        Response res = new Response();
        switch (action) {
            case P_CREATE:
                res.setStatusCode(ResponseCodes.CREATED);
                res.setMessage("Product successfully created");
                break;
            case P_INFO:
                res.setStatusCode(ResponseCodes.OK);
                res.setMessage("OK");
                break;
            case P_EDIT:
                res.setStatusCode(ResponseCodes.OK);
                res.setMessage("Product successfully updated");
                break;
            case P_REMOVE:
                res.setStatusCode(ResponseCodes.OK);
                res.setMessage("Product successfully removed");
                break;
            case S_ADD, S_SELL, S_LOSS:
                res.setStatusCode(ResponseCodes.OK);
                res.setMessage("Stok updated");
                break;
        }
        res.setData(resData);

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

    private void setData(Object data) {
        this.data = data;
    }
}
