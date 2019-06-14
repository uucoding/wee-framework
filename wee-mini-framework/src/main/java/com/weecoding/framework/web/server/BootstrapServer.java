package com.weecoding.framework.web.server;

import com.weecoding.framework.constants.GlobalConstants;
import com.weecoding.framework.web.servlet.DispatcherServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

/**
 * @author : wee
 * @Date 2019-06-13  21:34
 */
public class BootstrapServer {

    /**声明一个tomcat容器*/
    private Tomcat tomcat;

    /**tomcat启动参数*/
    private String[] args;

    public BootstrapServer(String[] args) {
        this.args = args;
    }

    public void start() throws LifecycleException {
        tomcat = new Tomcat();
        /*设置tomcat端口和启动tomcat*/
        tomcat.setPort(8300);
        tomcat.start();

        //舒适化tomcat的容器
        Context context = new StandardContext();
        //设置context路径（为空即可）
        context.setPath("");
        //设置生命周期监听器
        context.addLifecycleListener(new Tomcat.FixContextListener());

        //把自定义的servlet装载到tomcat上，并设置其支持异步
        Tomcat.addServlet(context, DispatcherServlet.class.getSimpleName(), new DispatcherServlet())
                .setAsyncSupported(true);

        //设置servlet的 映射(参数1：处理请求的路径，参数二映射的servlet),
        context.addServletMappingDecoded(GlobalConstants.PATTERN, DispatcherServlet.class.getSimpleName());

        //tomcat的context容器依附host容器,建立tomcat和servlet联系
        tomcat.getHost().addChild(context);

        /*为了防止服务器退出，增加一个等待线程*/
        Thread awaitThread = new Thread("tomcat_await_thread"){
            @Override
           public void run(){
               BootstrapServer.this.tomcat.getServer().await();
            }
        };

        //设置为用户线程，主线程结束后，用户线程还会继续运行
        awaitThread.setDaemon(false);

        awaitThread.start();
    }
}
