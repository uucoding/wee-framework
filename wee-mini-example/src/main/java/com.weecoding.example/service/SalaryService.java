package com.weecoding.example.service;

import com.weecoding.framework.beans.Bean;

/**
 * 测试@bean注解
 * @author : wee
 * @version : v1.0
 * @Date 2019-06-14  15:16
 */
@Bean
public class SalaryService {

    public Integer calculate(Integer money) {
        return money * 12;
    }
}
