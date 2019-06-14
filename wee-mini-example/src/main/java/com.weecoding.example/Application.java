package com.weecoding.example;

import com.weecoding.framework.starter.MiniApplication;

/**
 * @author : wee
 * @Description: 启动类
 * @Date 2019-06-13  17:08
 */
public class Application {

    public static void main(String[] args) {
        System.out.println("wee-mini-example hello word");
        MiniApplication.run(Application.class, args);
    }
}
