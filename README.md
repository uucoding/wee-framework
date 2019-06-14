# wee-framework
仿SpringMVC实现基础的请求转发

参考[慕课网](https://coding.imooc.com/learn/list/350.html)

### 项目结构
采用gradle多模块构建

wee-mini-example 依赖 wee-mini-framework

其中wee-mini-framework可以打包上传本地仓库，然后被example引用，或者直接引用模块
```
# 打包上传本地仓库
gradle uploadArchives
```
wee-mini-framework整体文件结构描述
```
├── build.gradle  多模块通用配置（包括打包上传本地的配置）
├── settings.gradle
├── wee-mini-example
│   ├── build.gradle 示例配置（包括引入本地仓库的wee-mini-framework打的jar和直接引入模块两种方式）
│   └── src
│       ├── main
│           ├── java
│               └── com.weecoding.example
│                   ├── controller
│                   └── service
└── wee-mini-framework
    ├── build.gradle 框架配置（内置tomcat）
    └── src
        ├── main
            ├── java
                └── com
                    └── weecoding
                        └── framework
                            ├── beans 管理bean的相关注解和工厂
                            ├── constants 常量类
                            ├── core  类的扫描器
                            ├── starter 框架入口
                            └── web   管理web相关
                                ├── handler  web请求的助手类
                                ├── mvc 管理请求的相关注解
                                ├── server 服务启动类
                                └── servlet  web请求的调度类 
```
 


### 项目启动
构建可执行jar
```
gradle clean build
```
执行jar包
```
java -jar wee-mini-example/build/libs/wee-mini-example-0.0.1.jar
```


