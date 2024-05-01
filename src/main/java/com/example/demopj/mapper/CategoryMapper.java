package com.example.demopj.mapper;

import com.example.demopj.domain.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("select * from category where create_user = #{id}")
    public List<Category> getAllCategory(int id);

    @Insert("insert into category(category_name, category_alias, create_user, create_time, update_time) " +
            "values (#{categoryName}, #{categoryAlias}, #{createUser}, #{createTime}, #{updateTime})")
    public void addCategory(Category category);

    @Select("select * from category where id=#{id}")
    public Category getCategoryById(int id);

    @Update("update category set category_name=#{categoryName}, category_alias=#{categoryAlias}, " +
            "update_time=#{updateTime} where id=#{id}")
    public void updateCategory(Category category);

    @Delete("delete from category where id=#{id}")
    public void deleteCategory(int id);

}
