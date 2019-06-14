package com.weecoding.framework.web.handler;

import com.weecoding.framework.beans.BeanFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * mappingHandler
 * 主要用于处理{@link com.weecoding.framework.web.servlet.DispatcherServlet} 转发的uri
 * @author : wee
 * @Description:
 * @Date 2019-06-13  23:12
 */
public class MappingHandler {

    /**前端提交的uri*/
    private String uri;

    /**uri需要到达的请求controller方法*/
    private Method method;

    /**uri需要请求到达的controller*/
    private Class<?> controller;

    /**uri传递的参数*/
    private String[] args;

    public MappingHandler(String uri, Method method, Class<?> controller, String[] args) {
        this.uri = uri;
        this.method = method;
        this.controller = controller;
        this.args = args;
    }

    /**
     * 判断当前mappingHandler是否能处理请求
     * @param request
     * @param response
     * @return
     */
    public boolean handle (ServletRequest request, ServletResponse response) throws InvocationTargetException, IllegalAccessException, InstantiationException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();

        //如果当前请求的uri和当前mappingHandler绑定的uri不一致，表示无法请求
        if (!this.uri.equals(requestURI)) {
            return false;
        }
        //如果一致，执行controller的方法
        Object[] params;
        //获取uri的参数
        if (args != null && args.length > 0) {
            params = new Object[args.length];
            for (int i = 0; i < args.length; i++) {
                params[i] = httpServletRequest.getParameter(args[i]);
            }
        } else {
            params = new Object[]{};
        }
        //执行方法
        Object entry = BeanFactory.getBean(controller);
        Object result = method.invoke(entry, params);
        //将方法的返回数据设置到响应中
        response.getWriter().println(result.toString());
        return true;
    }
}
