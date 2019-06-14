package com.weecoding.framework.web.servlet;

import com.weecoding.framework.web.handler.HandlerManager;
import com.weecoding.framework.web.handler.MappingHandler;

import javax.servlet.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author : wee
 * @Date 2019-06-13  21:51
 */
public class DispatcherServlet implements Servlet {
    /**
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    /**
     * @return ServletConfig
     */
    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    /**
     * 自定义servlet实现此类即可
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        for (MappingHandler mappingHandler : HandlerManager.mappinghandlerList) {
            try {
                //判断是否允许执行
                if (mappingHandler.handle(request, response)) {
                    return;
                }
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return
     */
    @Override
    public String getServletInfo() {
        return null;
    }

    /**
     *
     */
    @Override
    public void destroy() {

    }
}
