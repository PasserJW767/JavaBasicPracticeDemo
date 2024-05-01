package com.example.demopj.controller;

import com.example.demopj.Utils.ThreadLocalUtil;
import com.example.demopj.domain.Category;
import com.example.demopj.service.CategoryService;
import com.example.demopj.type.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class categoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public Result<List<Category>> getAllCategory(){
        Map<String, Object> claims = ThreadLocalUtil.get();
        int id = (int) claims.get("id");

        List<Category> categoryList = categoryService.getAllCategory(id);
        return Result.success(categoryList);
    }

    @PostMapping
    public Result addCategory(@RequestBody @Validated(Category.Add.class) Category category){
        categoryService.addCategory(category);
        return Result.success();
    }

    @GetMapping("/detail")
    public Result getCategoryDetail(@RequestParam int id){
        Map<String, Object> claims = ThreadLocalUtil.get();
        int userId = (int) claims.get("id");
        Category category = categoryService.getCategoryById(id);
        if (category.getCreateUser() != userId)
            return Result.error("越权访问!");
        return Result.success(category);
    }

    @PutMapping
    public Result updateCategory(@RequestBody @Validated(Category.Update.class) Category category){
        Map<String, Object> claims = ThreadLocalUtil.get();
        int userId = (int) claims.get("id");
        Category categoryById = categoryService.getCategoryById(category.getId());
        if (categoryById.getCreateUser() != userId)
            return Result.error("越权访问!");
        categoryService.updateCategory(category);
        return Result.success();
    }

    @DeleteMapping
    public Result deleteCategory(@RequestParam int id){
        Map<String, Object> claims = ThreadLocalUtil.get();
        int userId = (int) claims.get("id");
        Category categoryById = categoryService.getCategoryById(id);
        if (categoryById.getCreateUser() != userId)
            return Result.error("越权访问!");
        categoryService.deleteCategory(id);
        return Result.success();
    }

}
