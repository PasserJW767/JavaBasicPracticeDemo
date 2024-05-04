package com.example.demopj.service;

import com.example.demopj.Utils.PageBean;
import com.example.demopj.domain.Article;
import com.example.demopj.domain.Category;

public interface ArticleService {

    public boolean add(Article article);

    public boolean update(Article article);

    public Article getById(int id);

    public boolean delete(int id);

//    条件分页列表查询
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);
}
