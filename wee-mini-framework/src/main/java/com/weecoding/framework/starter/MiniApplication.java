package com.weecoding.framework.starter;

import com.weecoding.framework.beans.BeanFactory;
import com.weecoding.framework.core.ClassScanner;
import com.weecoding.framework.web.handler.HandlerManager;
import com.weecoding.framework.web.server.BootstrapServer;
import org.apache.catalina.LifecycleException;

import java.io.IOException;
import java.util.List;

/**
 * mini-framework入口类
 * @author : wee
 * @version : v1.0
 * @Date 2019-06-13  17:09
 */
public class MiniApplication {

    /**
     * 入口类，一般会传递应用类的入口类，
     * 通过应用入口类，可以获取应用的根目录，这样就可以获取应用类的相关信息，然后进行操作
     * @param cls
     * @param args
     */
    public static void run(Class<?> cls, String[] args) {
        System.out.println(cls.getName() + " 调用 wee-mini-framework hello word");

        System.err.println("启动服务器...");
        BootstrapServer bootstrapServer = new BootstrapServer(args);

        try {
            bootstrapServer.start();
            //扫描所有类
            List<Class<?>> classList = ClassScanner.scanClasses(cls.getPackage().getName());
            classList.forEach(e -> System.out.println(e.getName()));
            //实例化所有bean
            BeanFactory.initBean(classList);
            //初始化所有handler
            HandlerManager.resolveMappingHandler(classList);
            System.err.println("服务器启动成功...");
        } catch (LifecycleException e) {
            System.err.println("服务器启动失败...");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
