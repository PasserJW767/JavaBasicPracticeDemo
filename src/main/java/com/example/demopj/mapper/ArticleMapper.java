package com.example.demopj.mapper;

import com.example.demopj.anno.State;
import com.example.demopj.domain.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {

    @Insert("insert into article(title, content, cover_img, state, category_id, create_user, create_time, update_time) " +
            "values (#{title}, #{content}, #{coverImg}, #{state}, #{categoryId}, #{createUser}, now(), now())")
    public void add(Article article);

    @Select("select * from article where id=#{id}")
    public Article getById(Integer id);

    @Update("update article set title=#{title}, content=#{content}, cover_img=#{coverImg}, state=#{state}, " +
            "category_id=#{categoryId}, update_time=now() where id=#{id}")
    public void update(Article article);

    @Delete("delete from article where id=#{id}")
    public void delete(int id);

    List<Article> list(Integer userId, Integer categoryId, String state);

}
