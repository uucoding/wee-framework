package com.weecoding.framework.beans;

import java.lang.annotation.*;

/**
 * 标记bean的注解
 * @author : wee
 * @version v1.0
 * @Date 2019-06-14  14:36
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Bean {
}
