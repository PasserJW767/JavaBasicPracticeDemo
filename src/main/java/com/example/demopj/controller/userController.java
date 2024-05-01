package com.example.demopj.controller;

import com.example.demopj.Utils.JwtUtil;
import com.example.demopj.Utils.Md5Util;
import com.example.demopj.Utils.ThreadLocalUtil;
import com.example.demopj.domain.User;
import com.example.demopj.service.UserService;
import com.example.demopj.type.Result;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public Result<String> register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password){
        User user = userService.findByUsername(username);
        if (user != null)
            return Result.error("用户名已存在");
        userService.insertUser(username, password);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password){
        User user = userService.findByUsername(username);
        if (user == null)
            return Result.error("用户不存在");
        boolean passwordValidation = Md5Util.checkPassword(password, user.getPassword());
        if (passwordValidation){
            Map<String, Object> claims= new HashMap<String, Object>();
            claims.put("id", user.getId());
            claims.put("username", user.getUsername());
            String jwtData = JwtUtil.genToken(claims);
            return Result.success(jwtData);
        } else {
            return Result.error("密码错误");
        }
    }

    @GetMapping("/userInfo")
    public Result<User> getUserInfo(){
        Map<String, Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("username");
        User user = userService.findByUsername(username);
        return Result.success(user);
    }

    @PostMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.updateUser(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePassword")
    public Result updatePassword(@RequestBody Map<String, String> params){
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (oldPwd == null || newPwd == null || rePwd == null || !(oldPwd.length() > 0) || !(newPwd.length() > 0) || !(rePwd.length() > 0))
            return Result.error("输入内容不能为空！");

        if (!newPwd.equals(rePwd))
            return Result.error("两次输入的密码不一致");

        Map<String, Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("username");
        User user = userService.findByUsername(username);
        if (!Md5Util.checkPassword(oldPwd, user.getPassword()))
            return Result.error("原密码输入错误");

        userService.updatePassword(newPwd, user.getId());
        return Result.success();
    }

}
