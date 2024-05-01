package com.example.demopj.mapper;

import com.example.demopj.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Select("select * from user where username = #{username}")
    public User findByUsername(String username);

    @Insert("insert into user (username, password, create_time, update_time) values (#{username}, #{password}, now(), now())")
    public void insertUser(String username, String password);

    @Update("update user set nickname=#{nickname}, email=#{email}, update_time=now() where id=#{id}")
    public void updateUser(User user);

    @Update("update user set userPic=#{avatarUrl}, update_time=now()s where id=#{id}")
    public void updateAvatar(String avatarUrl, int id);

    @Update("update user set password=#{pwd}, update_time=now() id=#{id}")
    public void updatePassword(String pwd, int id);
}
