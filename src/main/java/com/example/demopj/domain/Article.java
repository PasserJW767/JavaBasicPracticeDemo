package com.example.demopj.domain;

import com.example.demopj.anno.State;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
public class Article {
    @NotNull(groups = Update.class)
    private Integer id; // 主键Id
    @NotEmpty(groups = {Add.class, Update.class})
    @Pattern(regexp = "^\\S{1,10}$", groups = {Add.class, Update.class})
    private String title;
    @NotEmpty(groups = {Add.class, Update.class})
    private String content;
    @NotEmpty(groups = {Add.class, Update.class})
    @URL(groups = {Add.class, Update.class})
    private String coverImg;
    @State(groups = {Add.class, Update.class})
    private String state; // 发布状态 已发布|草稿
    @NotNull(groups = {Add.class, Update.class})
    private Integer categoryId; // 文章分类Id
    @JsonIgnore
    private Integer createUser; // 创建人Id
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public interface Add{

    }

    public interface Update{

    }

}
