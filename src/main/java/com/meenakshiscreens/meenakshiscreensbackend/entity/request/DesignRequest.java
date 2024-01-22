package com.meenakshiscreens.meenakshiscreensbackend.entity.request;

import com.meenakshiscreens.meenakshiscreensbackend.entity.Category;

import javax.validation.constraints.NotNull;

public class DesignRequest {

    @NotNull
    private String designName;

    private String img;

    @NotNull
    private Long categoryId;

    public String getDesignName() {
        return designName;
    }

    public void setDesignName(String designName) {
        this.designName = designName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
