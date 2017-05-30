# HxdPluginClient
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://192.168.1.101/weibing/HxdPluginClient/blob/master/LICENSE)
[![OpenTracing Badge](https://img.shields.io/badge/OpenTracing-enabled-blue.svg)](http://opentracing.io)

[preview1]: http://192.168.60.253/xwiki/bin/download/Main/IceWee%E4%B8%AA%E4%BA%BA%E4%B8%93%E7%94%A8%E5%B8%96/WebHome/2017-01-23_133217.png
[preview2]: http://192.168.60.253/xwiki/bin/download/Main/IceWee%E4%B8%AA%E4%BA%BA%E4%B8%93%E7%94%A8%E5%B8%96/WebHome/2017-01-23_133229.png

## 简介
>惠下单®接口调试工具，用来模拟手机端或其他客户端请求惠下单后台接口服务的调试工具，请求JSON串与返回数据使用了AES加/解密以及BASE64等技术。  
本工具基于[NetBeans 8.2](https://netbeans.org/)、[JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
及[Apache HttpComponents 4.x](https://hc.apache.org/downloads.cgi)以及一些其他的开源类库编写而成。  
该项目属于私人学习项目，如果发现有任何不能接受的行为，请发邮件至[icewee@126.com](mailto:icewee@126.com?subject=HxdPluginClient Feedback)。

## 下载
>项目源码需要使用[GIT克隆](git@192.168.1.101:weibing/HxdPluginClient.git)到你的本地。

## 构建

#### 1.克隆项目到本地
```perl
git clone git@192.168.1.101:weibing/HxdPluginClient.git
```
#### 2.替换安全策略文件 
>将`HxdPluginClient/src/main/lib`目录下的`local_policy.jar`和`US_export_policy.jar`复制到`%JRE_HOME%/lib/security`目录下替换已有同名jar文件，如：`D:\apps\Java\jre1.8.0_112_x64\lib\security`，不做该操作会导致AES加密解密失败。

#### 3.进入项目目录
```perl
cd ./HxdPluginClient
```
#### 4.安装Swing皮肤
```perl
mvn install:install-file -Dfile=./src/main/lib/substance-4.0.jar -DgroupId=org.jvnet -DartifactId=substance -Dversion=4.0.RELEASE -Dpackaging=jar
```
#### 5.MAVEN打包
```perl
mvn package
```
#### 6.运行工具
>到`HxdPluginClient/target/HxdPluginClient`目录下，双击`运行.bat`即可启动本工具。

## 预览
#### 主界面
![alt 预览][preview1]

#### 更换主题
![alt 预览][preview2]

## 版权
>MIT © 2017 `IceWee`











