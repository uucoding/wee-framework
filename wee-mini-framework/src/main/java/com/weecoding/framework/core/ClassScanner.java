package com.weecoding.framework.core;

import com.weecoding.framework.constants.GlobalConstants;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类扫描器
 * @author : wee
 * @Description:
 * @Date 2019-06-13  22:19
 */
public class ClassScanner {

    /**
     * 获取所有扫描到的类
     * @param packageName  包名
     * @return
     */
    public static List<Class<?>> scanClasses(String packageName) throws IOException, ClassNotFoundException {
        List<Class<?>> classList = new ArrayList<>();

        /*将包名转化为文件路径*/
        String path = packageName.replace(GlobalConstants.POINT, GlobalConstants.PATTERN);
        //使用类加载器
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        //获取包下的URL资源
        Enumeration<URL> resources = classLoader.getResources(path);
        
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            //获取资源类型，最后打包运行的是jar，那么处理资源类型是jar的情况
            if (resource.getProtocol().contains(GlobalConstants.RESOURCE_TYPE)) {
                //获取jar的链接
                JarURLConnection jarURLConnection = (JarURLConnection)resource.openConnection();
                //获取jar的路径名
                String jarFilePath = jarURLConnection.getJarFile().getName();
                System.out.println("jar的路径为：<==" + jarFilePath);
                classList.addAll(getClassesFromJar(jarFilePath, path));
            } else {
                System.out.println("不处理的资源类型为：<==" + resource.getProtocol());
            }
        }
        return classList;
    }
    /**
     * 通过jar路径获取jar指定包的类
     * @param jarFilePath jar的路径
     * @param packagePath 类的相对路径
     * @return
     */
    private static List<Class<?>> getClassesFromJar(String jarFilePath, String packagePath) throws IOException, ClassNotFoundException {
        List<Class<?>> classList = new ArrayList<>();
        /*将jar路径转化成JarFile实例*/
        JarFile jarFile = new JarFile(jarFilePath);
        /*获取jarfile中的实体*/
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            /*entryName的内容/com/weecoding/example/Application.class*/
            String entryName = jarEntry.getName();
            //判断是否是我们需要的包下面的类，且文件必须是.class结尾的文件
            if (entryName.startsWith(packagePath) &&
                entryName.endsWith(GlobalConstants.POINT_CLASS)) {
                //包含包名的实体名
                String entryPackageName = entryName.replaceAll(GlobalConstants.PATTERN, GlobalConstants.POINT)
                                                    .substring(0, entryName.length() - 6);
                //通过类加载器将 类加载到jvm

                classList.add(Class.forName(entryPackageName));
            } else {

            }
        }
        return classList;
    }
}
