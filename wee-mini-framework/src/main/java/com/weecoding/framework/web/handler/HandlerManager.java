package com.weecoding.framework.web.handler;

import com.weecoding.framework.web.mvc.Controller;
import com.weecoding.framework.web.mvc.RequestMapping;
import com.weecoding.framework.web.mvc.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * handler管理类
 * @author : wee
 * @Date 2019-06-13  23:17
 */
public class HandlerManager {

    public static List<MappingHandler> mappinghandlerList = new ArrayList<>();


    /***
     * 将被{@link com.weecoding.framework.web.mvc.Controller}注解的类解析到Mappinghandler中
     * @param classes
     */
    public static void resolveMappingHandler(List<Class<?>> classes) {
        for (Class<?> cls : classes) {
            //挑出被@Controller注解的类
            if (cls.isAnnotationPresent(Controller.class)) {
                //解析controller类
                parseHandlerFromController(cls);
            }
        }
    }

    /**
     * 解析类
     * @param controller
     */
    private static void parseHandlerFromController(Class<?> controller) {

        //获取controller的所有方法
        Method[] methods = controller.getDeclaredMethods();

        for (Method method : methods) {
            //筛选出被RequestMapping注解的方法
            if (method.isAnnotationPresent(RequestMapping.class)) {
                //获取RequestMapping注解的值
                String uri = method.getDeclaredAnnotation(RequestMapping.class).value();

                //获取方法参数的值
                Parameter[] parameters = method.getParameters();
                List<String> parameterList = new ArrayList<>();
                if (parameters != null && parameters.length > 0) {
                    for (Parameter parameter : parameters) {
                        //判断参数是否被RequestParam注解
                        if (parameter.isAnnotationPresent(RequestParam.class)) {
                            //获取所有参数的的key
                            parameterList.add(parameter.getDeclaredAnnotation(RequestParam.class).value());
                        }
                    }
                }
                //将集合转化成数组
                String[] params = parameterList.toArray(new String[parameterList.size()]);

                //构建MappingHandler

                MappingHandler mappingHandler = new MappingHandler(uri, method, controller, params);

                HandlerManager.mappinghandlerList.add(mappingHandler);
            }
        }

    }
}
