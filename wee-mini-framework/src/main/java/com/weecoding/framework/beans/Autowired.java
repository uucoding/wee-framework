package com.weecoding.framework.beans;

import java.lang.annotation.*;

/**
 *  注入注解
 * @author : wee
 * @version v1.0
 * @Date 2019-06-14  14:39
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Autowired {
}
