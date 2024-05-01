package com.example.demopj.service.impl;


import com.example.demopj.Utils.Md5Util;
import com.example.demopj.Utils.ThreadLocalUtil;
import com.example.demopj.domain.User;
import com.example.demopj.mapper.UserMapper;
import com.example.demopj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public void insertUser(String username, String password) {
        String Md5str = Md5Util.getMD5String(password);
        userMapper.insertUser(username, Md5str);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        int id = (int) claims.get("id");
        userMapper.updateAvatar(avatarUrl, id);
    }

    @Override
    public void updatePassword(String pwd, int id) {
        String md5String = Md5Util.getMD5String(pwd);
        userMapper.updatePassword(md5String, id);
    }
}
