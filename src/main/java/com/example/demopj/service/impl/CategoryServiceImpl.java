package com.example.demopj.service.impl;

import com.example.demopj.Utils.ThreadLocalUtil;
import com.example.demopj.domain.Category;
import com.example.demopj.mapper.CategoryMapper;
import com.example.demopj.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> getAllCategory(int id) {
        return categoryMapper.getAllCategory(id);
    }

    @Override
    public void addCategory(Category category) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        category.setCreateUser((Integer) claims.get("id"));
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.addCategory(category);
    }

    @Override
    public Category getCategoryById(int id) {
        return categoryMapper.getCategoryById(id);
    }

    @Override
    public void updateCategory(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.updateCategory(category);
    }

    @Override
    public void deleteCategory(int id) {
        categoryMapper.deleteCategory(id);
    }
}
