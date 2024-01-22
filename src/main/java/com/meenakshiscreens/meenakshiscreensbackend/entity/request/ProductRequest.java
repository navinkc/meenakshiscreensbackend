package com.meenakshiscreens.meenakshiscreensbackend.entity.request;

import javax.validation.constraints.NotBlank;

public class ProductRequest {

    @NotBlank
    private String productName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
