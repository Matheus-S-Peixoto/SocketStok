package org.stok.protocol.response;

import org.stok.model.Product;
import org.stok.service.StokService;

import java.math.BigDecimal;
import java.util.List;

public class ResponseData {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal amount;
    private String code;
    private Integer quantity;

    public Object parseServiceResult(Object serviceResult) {
        if(serviceResult instanceof List<?> productList) {
            return productList;
        } else if (serviceResult instanceof Product product) {
            ResponseData resData = new ResponseData();

            resData.setId(product.getId());
            resData.setName(product.getName());
            resData.setDescription(product.getDescription());
            resData.setAmount(product.getAmount());
            resData.setCode(product.getCode());
            resData.setQuantity(product.getQuantity());

            return resData;
        } else {
            return null;
        }
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
