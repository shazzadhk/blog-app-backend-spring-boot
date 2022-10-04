package com.example.jwtdemo.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CategoryDto {

    @NotBlank
    @Size(min = 4,message = "Minimum size of category title is 4")
    private String categoryTitle;

    @NotBlank
    @Size(min = 10,message = "Minimum size of category Description is 10")
    private String categoryDesc;

    public CategoryDto() {
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }
}
