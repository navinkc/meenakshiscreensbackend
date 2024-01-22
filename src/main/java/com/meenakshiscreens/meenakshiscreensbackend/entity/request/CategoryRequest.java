package com.meenakshiscreens.meenakshiscreensbackend.entity.request;

import com.meenakshiscreens.meenakshiscreensbackend.entity.Design;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class CategoryRequest {

    @NotBlank
    private String categoryName;

    @NotNull
    private Long productId;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
