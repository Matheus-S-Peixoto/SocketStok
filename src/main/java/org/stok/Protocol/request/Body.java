package org.stok.Protocol.request;

import java.math.BigDecimal;

public class Body {
    private String name;
    private String description;
    private BigDecimal amount;
    private String code;
    private Integer quantity;

    public boolean isEmpty() {
        return (getName() == null) && (getDescription() == null) && (getAmount() == null) && (getCode() == null) && (getQuantity() == null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
