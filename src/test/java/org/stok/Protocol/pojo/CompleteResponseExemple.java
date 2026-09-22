package org.stok.Protocol.pojo;

public class CompleteResponseExemple {
    private int statusCode;
    private String message;
    private ResDataExample data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResDataExample getData() {
        return data;
    }

    public void setData(ResDataExample data) {
        this.data = data;
    }
}
