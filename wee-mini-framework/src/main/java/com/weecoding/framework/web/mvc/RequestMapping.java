package com.weecoding.framework.web.mvc;

import java.lang.annotation.*;

/**
 * @author : wee
 * @version v1.0
 * @Description: todo
 * @Date 2019-06-13  22:13
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestMapping {

    /**
     * url
     * @return
     */
    String value();
}
