package com.example.jwtdemo.service;

import com.example.jwtdemo.dao.CategoryDao;
import com.example.jwtdemo.entity.Category;
import com.example.jwtdemo.exceptions.ApiException;
import com.example.jwtdemo.exceptions.ResourceNotFoundExceptions;
import com.example.jwtdemo.payloads.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    public CategoryDto addCategory(CategoryDto categoryDto){
        Category category = new Category();
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDesc());
        categoryDao.save(category);

        return categoryDto;
    }

    public CategoryDto getCategoryById(Integer categoryId){
        Category category = categoryDao.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundExceptions("category","categoryId",categoryId));
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryTitle(category.getCategoryTitle());
        categoryDto.setCategoryDesc(category.getCategoryDescription());

        return categoryDto;
    }

    public CategoryDto updateCategory(Integer categoryId,CategoryDto categoryDto){
        Category category = categoryDao.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundExceptions("category","categoryId",categoryId));

        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDesc());

        categoryDao.save(category);

        CategoryDto categoryDto1 = new CategoryDto();

        categoryDto1.setCategoryTitle(category.getCategoryTitle());
        categoryDto1.setCategoryDesc(category.getCategoryDescription());

        return categoryDto1;
    }

    public void deleteCategory(Integer categoryId){
        Category category = categoryDao.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundExceptions("category","categoryId",categoryId));
        categoryDao.deleteById(categoryId);
    }

}
