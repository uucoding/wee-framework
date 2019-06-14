package com.weecoding.framework.web.mvc;

import java.lang.annotation.*;

/**
 * @author : wee
 * @version v1.0
 * @Date 2019-06-13  22:13
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RequestParam {

    /**
     * 参数名称
     * @return
     */
    String value();
}
