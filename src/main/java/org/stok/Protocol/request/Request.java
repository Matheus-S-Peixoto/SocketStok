package org.stok.Protocol.request;

import org.stok.Protocol.Actions;

public class Request {
    private Actions action;
    private Integer id;
    private Body body;

    public Actions getAction() {
        return action;
    }

    public void setAction(Actions action) {
        this.action = action;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}
