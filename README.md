# Easymall_SSM
基于SSM的电商平台

<h4>一、项目创建准备工作</h4>

1.创建web项目<br>
2.创建WEB-INF/lib和WEB-INF/classes<br>
3.lib中导入jar包<br>
4.Modules中Paths配置 指向项目中的classes<br>
5.Modules中Dependencies的JARS or .... 指向项目的lib文件夹，勾选

<h4>二、配置SpringMVC</h4>

1.web/WEB-INF/web.xml （配置前端控制器指向springmvc.xml 、全站乱码过滤器）

2.src/springmvc.xml （包扫描、注解方式mvc、视图解析器）

<h4>三、配置Spring</h4>
1.web/WEB-INF/web.xml  (监听器)<br>
<br>
Controller层<br>
applicationContext.xml (包扫描、注解方式DI、注解方式AOP)
数据源、整合MyBatis


Service层<br>
接口(实现service类)+service类

Mybatis <br>
sqlMapConfig.xml  (无需配置，转移到spring自动配置)



