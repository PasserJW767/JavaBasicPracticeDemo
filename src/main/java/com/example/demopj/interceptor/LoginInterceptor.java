package com.example.demopj.interceptor;

import com.example.demopj.Utils.JwtUtil;
import com.example.demopj.Utils.ThreadLocalUtil;
import com.example.demopj.type.Result;
import com.fasterxml.jackson.datatype.jdk8.WrappedIOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        获得token
        String token = request.getHeader("Authorization");
//        验证token
        try{
            Map<String, Object> claims = JwtUtil.parseToken(token);

            String username = (String) claims.get("username");
            ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
            String token_in_redis = stringStringValueOperations.get(username);

            if (token_in_redis == null || !token_in_redis.equals(token)){
                throw new RuntimeException("登录令牌验证失败");
            }

            ThreadLocalUtil.set(claims);
            return true;
        } catch (Exception e){
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
