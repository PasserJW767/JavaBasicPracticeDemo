package com.example.demopj.service;

import com.example.demopj.domain.Category;

import java.util.List;

public interface CategoryService {

    public List<Category> getAllCategory(int id);

    public void addCategory(Category category);

    public Category getCategoryById(int id);

    public void updateCategory(Category category);

    public void deleteCategory(int id);

}
