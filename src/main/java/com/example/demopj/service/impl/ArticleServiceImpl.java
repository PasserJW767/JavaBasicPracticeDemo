package com.example.demopj.service.impl;

import com.example.demopj.Utils.PageBean;
import com.example.demopj.Utils.ThreadLocalUtil;
import com.example.demopj.domain.Article;
import com.example.demopj.domain.Category;
import com.example.demopj.mapper.ArticleMapper;
import com.example.demopj.mapper.CategoryMapper;
import com.example.demopj.service.ArticleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public boolean add(Article article) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        Category categoryById = categoryMapper.getCategoryById(article.getCategoryId());
        Integer createUser = categoryById.getCreateUser();
        if (createUser.intValue() != id.intValue())
            return false;

        article.setCreateUser(id);
        articleMapper.add(article);
        return true;
    }

    @Override
    public boolean update(Article article) {

        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");

        Article byId = articleMapper.getById(article.getId());
        Integer article_createUser = byId.getCreateUser();

        Category categoryById = categoryMapper.getCategoryById(article.getCategoryId());
        Integer category_createUser = categoryById.getCreateUser();

        if (id.intValue() != article_createUser.intValue() || id.intValue() != category_createUser.intValue())
            return false;

        articleMapper.update(article);

        return true;
    }

    @Override
    public Article getById(int id) {
        return articleMapper.getById(id);
    }

    @Override
    public boolean delete(int id) {

        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer loginUserid = (Integer) claims.get("id");

        Article byId = articleMapper.getById(id);

        if (byId.getCreateUser().intValue() != loginUserid.intValue())
            return false;

        articleMapper.delete(id);
        return true;
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {

    //        1. 创建PageBean对象
        PageBean<Article> pb = new PageBean<>();
    //        2. 开启分页查询
        PageHelper.startPage(pageNum, pageSize);
    //        3. 调用Mapper
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer loginUserid = (Integer) claims.get("id");
        List<Article> as = articleMapper.list(loginUserid, categoryId, state);
        Page<Article> p = (Page<Article>) as;

        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }
}
