package org.stok.protocol.pojo;

import org.stok.protocol.Actions;

public class RequestExemple {
    private Actions action;
    private Integer id;
    private BodyExemple body;

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

    public BodyExemple getBody() {
        return body;
    }
    public void setBody(BodyExemple body) {
        this.body = body;
    }
}
