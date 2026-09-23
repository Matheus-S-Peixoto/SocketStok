package org.stok.protocol.response;

import org.stok.model.Product;

import java.math.BigDecimal;
import java.util.List;

public class ResponseData {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal amount;
    private String code;
    private Integer quantity;

    public static Object from(Object serviceResult) {
        if (serviceResult instanceof Product product) {
            return from(product);
        }
        if (serviceResult instanceof List<?> products) {
            return products.stream().map(product -> from((Product) product)).toList();
        }
        return null;
    }

    public static ResponseData from(Product product) {
        ResponseData resData = new ResponseData();

        resData.setId(product.getId());
        resData.setName(product.getName());
        resData.setDescription(product.getDescription());
        resData.setAmount(product.getAmount());
        resData.setCode(product.getCode());
        resData.setQuantity(product.getQuantity());

        return resData;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
