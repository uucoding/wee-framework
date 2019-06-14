package com.weecoding.framework.beans;

import com.weecoding.framework.web.mvc.Controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * bean工厂
 * @author : wee
 * @version : v todo
 * @Date 2019-06-14  14:41
 */
public class BeanFactory {

    /**存放class-bean*/
    private static Map<Class<?>, Object> classToBean = new ConcurrentHashMap<>();

    /**
     * 根据class获取对应的bean
     * @param cls
     * @return
     */
    public static Object getBean(Class<?> cls) {
        return classToBean.get(cls);
    }

    /**
     * 初始化bean
     * @param classes
     */
    public static void initBean(List<Class<?>> classes) throws Exception {
        List<Class<?>> toCreate = new ArrayList<>(classes);

        /**遍历创建*/
        while (toCreate.size() != 0) {
            //剩余待创建的bean数量
            int remainCount = toCreate.size();
            for (int i = 0; i < toCreate.size(); i++) {
                if (finishCreate(toCreate.get(i))) {
                    toCreate.remove(i);
                }
            }
            //如果循环创建完毕后，数据相同，那么表示都没创建成功，有循环依赖的情况，跑出异常
            if (remainCount == toCreate.size()) {
                throw new Exception("【初始化bean】<== 循环依赖");
            }
        }

    }

    /**
     * 完成bean的创建
     * @param cls
     * @return
     */
    private static boolean finishCreate(Class<?> cls) throws IllegalAccessException, InstantiationException {
        //检查缓存中是否已经存在，如果存在，不需要重复创建
        Object bean = classToBean.get(cls);
        if (bean != null) {
            return true;
        }
        //如果不需要初始化成bean，也直接删除
        if (!cls.isAnnotationPresent(Bean.class) && !cls.isAnnotationPresent(Controller.class)) {
            return true;
        }

        //初始化bean
        bean = cls.newInstance();
        Field[] fields = cls.getDeclaredFields();
        if (fields.length > 0) {
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    //查看缓存中是否存在bean
                    Object childBean = classToBean.get(field.getType());
                    //不存在则不能创建
                    if (childBean == null) {
                        return false;
                    }
                    //设置属性可访问
                    field.setAccessible(true);
                    field.set(bean, childBean);
                }
            }
        }
        //成功创建后存入缓存
        classToBean.put(cls, bean);
        System.out.println(String.format("创建「%s」成功", cls.getSimpleName()));
        return true;
    }
}
