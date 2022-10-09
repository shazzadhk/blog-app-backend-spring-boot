package com.example.jwtdemo.service;

import com.example.jwtdemo.dao.CategoryDao;
import com.example.jwtdemo.entity.Category;
import com.example.jwtdemo.exceptions.ApiException;
import com.example.jwtdemo.exceptions.ResourceNotFoundExceptions;
import com.example.jwtdemo.payloads.CategoryDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ModelMapper modelMapper;

    public CategoryDto addCategory(CategoryDto categoryDto){
        Category category = new Category();
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        categoryDao.save(category);

        return this.modelMapper.map(category,CategoryDto.class);
    }

    public CategoryDto getCategoryById(Integer categoryId){
        Category category = categoryDao.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundExceptions("category","categoryId",categoryId));

        return this.modelMapper.map(category,CategoryDto.class);
    }

    public CategoryDto updateCategory(Integer categoryId,CategoryDto categoryDto){
        Category category = categoryDao.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundExceptions("category","categoryId",categoryId));

        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());

        categoryDao.save(category);

        return this.modelMapper.map(category,CategoryDto.class);
    }

    public void deleteCategory(Integer categoryId){
        Category category = categoryDao.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundExceptions("category","categoryId",categoryId));
        categoryDao.deleteById(categoryId);
    }

    public List<CategoryDto> getAllCategories(){

        List<Category> categoryList = categoryDao.findAll();

        List<CategoryDto> categoryDtoList = new ArrayList<>();

        categoryList.forEach((category) -> {
            CategoryDto categoryDto = this.modelMapper.map(category,CategoryDto.class);
            categoryDtoList.add(categoryDto);
        });

        return categoryDtoList;
    }

}
