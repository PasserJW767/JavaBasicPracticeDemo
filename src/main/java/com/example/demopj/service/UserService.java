package com.example.demopj.service;

import com.example.demopj.domain.User;

public interface UserService {

    public User findByUsername(String username);

    public void insertUser(String username, String password);

    public void updateUser(User user);

    public void updateAvatar(String avatarUrl);

    public void updatePassword(String pwd, int id);

}
