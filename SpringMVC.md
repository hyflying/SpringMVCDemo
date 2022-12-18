# SpringMVC

1.porm.xml导入依赖

```xml
<packaging>war</packaging>
<dependencies>
    <!-- SpringMVC -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.3.1</version>
    </dependency>

    <!-- 日志 -->
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>1.2.3</version>
    </dependency>

    <!-- ServletAPI -->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>3.1.0</version>
        <scope>provided</scope>
    </dependency>

    <!-- Spring5和Thymeleaf整合包 -->
    <dependency>
        <groupId>org.thymeleaf</groupId>
        <artifactId>thymeleaf-spring5</artifactId>
        <version>3.0.12.RELEASE</version>
    </dependency> 
</dependencies>
```

2.配置web.XML

```xml
<!-- 配置SpringMVC的前端控制器 -->
<servlet>
    <servlet-name>springMVC</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!-- 通过初始化参数指定SpringMVC配置文件的位置和名称 -->
    <init-param>
        <!-- contextConfigLocation为固定值 -->
        <param-name>contextConfigLocation</param-name>
        <!-- 使用classpath:表示从类路径查找配置文件，例如maven工程中的src/main/resources -->
        <param-value>classpath:springMVC.xml</param-value>
    </init-param>
    <!--
  作为框架的核心组件，在启动过程中有大量的初始化操作要做
  而这些操作放在第一次请求时才执行会严重影响访问速度
  因此需要通过此标签将启动控制DispatcherServlet的初始化时间提前到服务器启动时
-->
    <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
    <servlet-name>springMVC</servlet-name>
    <!--
        设置springMVC的核心控制器所能处理的请求的请求路径
        /所匹配的请求可以是/login或.html或.js或.css方式的请求路径
        但是/不能匹配.jsp请求路径的请求
    -->
    <url-pattern>/</url-pattern>
</servlet-mapping>
```

3.创建请求控制器

```java
@Controller
public class HelloController {
    @RequestMapping("/")
    public String portal() {
      //返回逻辑视图 
        return "index";
    }
}
```

4.创建SpringMVC配置文件

```xml
 <!-- 自动扫描包 -->
 <context:component-scan base-package="com.example.mvc.controller"/>

 <!-- 配置Thymeleaf视图解析器 -->
 <bean id="viewResolver" class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
     <property name="order" value="1"/>
     <property name="characterEncoding" value="UTF-8"/>
     <property name="templateEngine">
         <bean class="org.thymeleaf.spring5.SpringTemplateEngine">
             <property name="templateResolver">
                 <bean class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">
                     <!-- 视图前缀 -->
                     <property name="prefix" value="/WEB-INF/templates/"/>
                     <!-- 视图后缀 -->
                     <property name="suffix" value=".html"/>
                     <property name="templateMode" value="HTML5"/>
                     <property name="characterEncoding" value="UTF-8" />
                 </bean>
             </property>
         </bean>
     </property>
 </bean>
```



# @RequstMapping

- 标识的位置

​			@RequestMapping标识一个类：设置映射请求的请求路径的初始信息

​			@RequestMapping标识一个方法：设置映射请求路径的具体信息

- value属性

​			@RequestMapping({"/hello", "/abc"})

​			匹配多个请求路径

- method属性限制请求方式

  ​	 @RequestMapping(value = {"/hello", "abc"}, method = RequestMethod.POST) 

- 在@RequestMapping的基础上，结合请求方式的一些派生注解
  			1. @GetMapping
  			1. @PostMapping
  			1. @DeleteMapping
  			1. @PutMapping

- params属性
  		1. "param":  当前所匹配请求的请求参数中必须携带param
  		1. "!param":当前所匹配请求的请求参数中必须不携带param
  		1. "param=value":当前所匹配请求的请求参数中必须携带param且值必须为value
  		1. "param!=value":当前所匹配请求的请求参数中可以不携带param，若携带值一定不能是value

- ant风格
      	1. ？：任意单个字符
      	1. *：任意0到多个字符
      	1. **：任意层数的任意目录

- 支持路径中的占位符

```html
<a th:href="@{/testrest/admin/1}">测试路径中的占位符</a>
```

```java
 @RequestMapping("/testrest/{username}/{id}")
 public String testrest(@PathVariable("username") String username, @PathVariable("id") String id) {
     return "success";
 }
```

# 获取请求参数

```java
@RequestMapping("/mvc")
public String getParam(@RequestParam(value = "user", required = false, defaultValue = "hello") String username,
                       @RequestParam(value = "pwd", required = true) String password) {
    return "success";
}
```

```java
    @RequestMapping("/param/pojo")
    public String getParamByPojo(User user) {
        System.out.println(user);
        return "success";
    }
```

# 域对象共享数据

- modelAndView

```java
    @RequestMapping("/test/mav")
    public ModelAndView testMAV() {
        ModelAndView mav = new ModelAndView();
        /**
         *ModelAndView包含Model和View的功能
         *Model：向请求域中共享数据
         *View：设置逻辑视图实现页面跳转
         */
        mav.addObject("testRequestScope","hello,ModelAndView");
        mav.setViewName("success");
        return mav;
    }
```



- model

```java
    @RequestMapping("/test/model")
    public String testModel(Model model) {

        model.addAttribute("testRequestScope", "Hello Model");

        // 设置逻辑视图
        return "success";
    }
```



- modelMap

```java
    @RequestMapping("/test/modelMap")
    public String testNodelMap(ModelMap modelMap) {
        modelMap.addAttribute("testRequestScope","Hello,ModelMap");
        return "success";
    }
```

底层都是用的BindingAwareModelMap类

# RESTful

- 查询： user/1 -->get请求方式
- 保存：user -->post请求方式
- 删除：user/1 -->delete请求方式
- 更新：user -->put请求方式

  浏览器只能发送get和post请求，若要发送put或delete请求需要在web.xml中配置一个过滤器HiddenHttpMethodFilter

```xml
<filter>
    <filter-name>HiddenHttpMethodFilter</filter-name>
    <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>HiddenHttpMethodFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

配置完过滤器之后，发送的请求需要满足两个条件

1. 当前请求方式必须为post
2. 当前请求必须传输请求参数_method，_method的值才是最终的请求

```html
<form th:action="@{/user}" method="post">
  <input type="hidden" name="_method" value="put">
  <input type="submit" value="修改用户信息">
</form>
```

```java
@Controller
public class TestRestController {
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getAllUser() {
        System.out.println("user---->get");
        return "success";
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String getUserById(@PathVariable("id") Integer id) {
        System.out.println("get查询用户信息user id = "+id);
        return "success";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String insertUser() {
        System.out.println("post添加用户信息");
        return "success";
    }
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public String updateUser() {
        System.out.println("put修改用户信息");
        return "success";
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable("id") Integer id) {
        System.out.println("delete删除用户信息，用户id为"+id);
        return "success";
    }
}
```



# ajax



## 文件下载

 服务器传到浏览器

```java
@RequestMapping("/test/down")
public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException {
    //获取ServletContext对象
    ServletContext servletContext = session.getServletContext();
    //获取服务器中文件的真实路径
    String realPath = servletContext.getRealPath("static/img");
    realPath = realPath + File.separator + "1.jpg";
    //创建输入流
    InputStream is = new FileInputStream(realPath);
    //创建字节数组，is.available()获取输入流所对应文件的字节数
    byte[] bytes = new byte[is.available()];
    //将流读到字节数组中
    is.read(bytes);
    //创建HttpHeaders对象设置响应头信息
    MultiValueMap<String, String> headers = new HttpHeaders();
    //设置要下载方式以及下载文件的名字
    headers.add("Content-Disposition", "attachment;filename=1.jpg");
    //设置响应状态码
    HttpStatus statusCode = HttpStatus.OK;
    //创建ResponseEntity对象
    ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, statusCode);
    //关闭输入流
    is.close();
    return responseEntity;
}
```

## 文件上传

浏览器传到服务

1. 引入依赖

```xml
<dependency>
    <groupId>commons-fileupload</groupId>
    <artifactId>commons-fileupload</artifactId>
    <version>1.4</version>
</dependency>
```

2. SpringMVC.xml配置bean,必须要配置id="multipartResolver"

```xml
<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver"></bean>
```

3. html 表单要有enctype属性。注意头像后跟着的input的name属性的值，后面java代码的行参要和这个值相同

```html
<form th:action="@{/test/up}" method="post" enctype="multipart/form-data">
    头像：<input type="file" name="photo"><br>
    <input type="submit" value="上传">
</form>
```

4. java代码

```java
@RequestMapping("/test/up")
public String testUp(MultipartFile photo, HttpSession session) throws IOException {
    //获取上传的文件的文件名
    String fileName = photo.getOriginalFilename();
    //获取上传的文件的后缀名
    String hzName = fileName.substring(fileName.lastIndexOf("."));
    //获取uuid
    String uuid = UUID.randomUUID().toString();
    //拼接一个新的文件名
    fileName = uuid + hzName;
    //获取ServletContext对象
    ServletContext servletContext = session.getServletContext();
    //获取当前工程下photo目录的真实路径
    String photoPath = servletContext.getRealPath("photo");
    //创建photoPath所对应的File对象
    File file = new File(photoPath);
    //判断file所对应目录是否存在
    if(!file.exists()){
        file.mkdir();
    }
    String finalPath = photoPath + File.separator + fileName;
    //上传文件
    photo.transferTo(new File(finalPath));
    return "success";
}
```

# 异常处理

- xml配置

```xml
    <!-- 异常处理器 -->
    <bean class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
        <!-- 配置异常 和 逻辑视图的映射 -->
        <property name="exceptionMappings">
            <props>
              <!-- 配置异常对应的页面 -->
                <prop key="java.lang.ArithmeticException">error</prop>
            </props>
        </property>
    </bean>
```

# SpringMVC执行流程

## 常用组件



