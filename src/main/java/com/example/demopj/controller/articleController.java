package com.example.demopj.controller;

import com.example.demopj.Utils.JwtUtil;
import com.example.demopj.Utils.PageBean;
import com.example.demopj.Utils.ThreadLocalUtil;
import com.example.demopj.domain.Article;
import com.example.demopj.service.ArticleService;
import com.example.demopj.type.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class articleController {

    @Autowired
    ArticleService articleService;

    @GetMapping("/detail")
    public Result<Article> detail(@RequestParam int id){
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer loginUserId = (Integer) claims.get("id");

        Article byId = articleService.getById(id);
        if (byId.getCreateUser().intValue() != loginUserId.intValue()){
            return Result.error("越权访问！");
        }
        return Result.success(byId);
    }

    @PostMapping
    public Result add(@RequestBody @Validated(Article.Add.class) Article article) {

        boolean add = articleService.add(article);
        if (add){
            return Result.success();
        } else {
            return Result.error("越权访问！");
        }

    }

    @PutMapping
    public Result update(@RequestBody @Validated(Article.Update.class) Article article){
        boolean update = articleService.update(article);
        if (update){
            return Result.success();
        } else {
            return Result.error("越权访问！");
        }
    }

    @DeleteMapping
    public Result deleteArticle(@RequestParam int id){
        boolean delete = articleService.delete(id);
        if (delete){
            return Result.success();
        } else {
            return Result.error("越权访问！");
        }
    }

    @GetMapping
    public Result<PageBean<Article>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state
    ){
        PageBean<Article> pb = articleService.list(pageNum, pageSize, categoryId, state);
        return Result.success(pb);
    }

}
