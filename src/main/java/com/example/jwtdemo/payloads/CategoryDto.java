package com.example.jwtdemo.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CategoryDto {

    private Integer categoryId;
    @NotBlank
    @Size(min = 4,message = "Minimum size of category title is 4")
    private String categoryTitle;

    @NotBlank
    @Size(min = 10,message = "Minimum size of category Description is 10")
    private String categoryDescription;

    public CategoryDto() {
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }
}
