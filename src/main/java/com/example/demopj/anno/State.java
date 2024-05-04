package com.example.demopj.anno;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StateValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface State {
//    提示信息
    String message() default "值只能为已发布/草稿";
//    指定分组
    Class<?>[] groups() default {};
//    负载，获取State注解的附加信息
    Class<? extends Payload>[] payload() default {};
}
