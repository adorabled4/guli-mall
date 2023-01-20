package com.dhx.gulimall.common.valid;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 自定义注解, 选择性的给值, 比如showStatus ,vals表示属性可以选择的值, 如果不是就会校验失败抛出message信息
 */
@Documented
@Constraint(
        validatedBy = {ListValueConstraintValidator.class} // 自定义校验功能
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ListValue {

    String message() default "{com.dhx.gulimall.common.valid.ListValue}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    int []vals() default{};
}
