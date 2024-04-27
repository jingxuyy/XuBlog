

# Blog

### 技术栈

- SpringBoot
- MybatisPlus
- SpringSecurity
- EasyExcel
- Swagger2
- redis
- Echarts
- Vue
- ElementUI
- MySQL

### 介绍

项目总体可以分为两个部分：1. 博客前台系统  2. 博客后台系统

### sg-blog-vue 为前台系统的前端源代码

### sg-vue-admin 为后台系统的前端源代码

### 其它为后端源代码

#### 博客前台系统

博客前台系统主要提供给游客浏览文章、评论、点赞等

主要页面：

1. **登录注册(过于简单不在展示)**
2. **首页**

![](C:\Users\86136\Desktop\XuBlog\README.assets\首页.png)

3. **首页文章浏览页面**

![](C:\Users\86136\Desktop\XuBlog\README.assets\首页详情.png)

4. **对于具体文章，可以点击查看详情**

![](C:\Users\86136\Desktop\XuBlog\README.assets\文章阅读.png)

5. **登录的用户可以给文章进行评论**

![](C:\Users\86136\Desktop\XuBlog\README.assets\文章评论.png)

#### 前台系统其它页面不在详细展示

### 博客后台系统

后台系统主要提供给用户进行文章管理，授权管理，以及最重要的写文章功能

#### 如果您是超级管理员，您可以看到：

- 超级管理员后台主页

![](C:\Users\86136\Desktop\XuBlog\README.assets\后台管理员主页.png)

超级管理员除了写文章还可以进行系统管理和内容管理。

- 超级管理员后台系统管理(用户管理)

![](C:\Users\86136\Desktop\XuBlog\README.assets\管理员进行用户管理.png)

- 超级管理员可以直接修改用户信息

![](C:\Users\86136\Desktop\XuBlog\README.assets\管理员修改用户信息.png)

- 超级管理员后台系统管理(角色管理)

![](C:\Users\86136\Desktop\XuBlog\README.assets\管理员管理角色.png)

![](C:\Users\86136\Desktop\XuBlog\README.assets\管理员修改角色.png)

- 超级管理员后台系统管理(菜单管理)

![](C:\Users\86136\Desktop\XuBlog\README.assets\管理员菜单管理.png)

- 超级管理员后台内容管理(文章管理)

![](C:\Users\86136\Desktop\XuBlog\README.assets\管理员进行文章管理.png)

其它分类、友联、标签管理类似

- 超级管理员后台写文章

![](C:\Users\86136\Desktop\XuBlog\README.assets\管理员写博文.png)

#### 普通管理员后台只能管理友联

![](C:\Users\86136\Desktop\XuBlog\README.assets\普通管理员.png)

### 普通用户只能写文章

![](C:\Users\86136\Desktop\XuBlog\README.assets\普通用户写博文.png)

### 整个项目非常简单，只涉及用户和文章。用户拥有角色，用来鉴权
