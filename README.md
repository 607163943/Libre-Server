# 图书馆管理系统 (Libre) - 服务端

本仓库为图书馆管理系统的后端服务。

## 🛠 功能模块

* 作者模块

* 出版社模板

* 图书模块

* 用户模块

* 角色模块

* 用户角色模块

* 借阅模块

* 登录模块

## 🛠️ 技术栈

* **核心框架**：`SpringBoot 2.6.13`
* **持久层框架**：`MyBatis-Plus3.5.3.1`
* **权限认证框架**：`Sa-Token1.45.0`
    * **无状态认证插件**：`Sa-Token-JWT1.45.0`
* **接口文档**：`Knife4j3.0.3`
* **工具**：`Hutool-All5.8.40`、`Lombok`
* **开发环境**：`JDK 8`、`MySQL 8.0.45`、`Maven3.9.9`、`Redis7.2`

## 🗄 数据库设计

系统核心包含以下基础表：
- `tb_book`：图书表
- `tb_author`：作者表
- `tb_publisher`：出版社表
- `tb_user`：用户表
- `tb_role`：角色表
- `tb_user_role`：用户角色中间表
- `tb_lend`：借阅表

## 🚀 快速启动

1. **数据库初始化**：
    - 创建名为 `libre` 的数据库。
    - 导入项目中 `sql` 文件夹下的数据库初始化脚本。
2. **环境配置**：
    - 修改 `src/main/resources/application.yaml` 中的数据库连接配置，替换为你的数据库用户名和密码。
3. **部署运行**：
    - 启动 SpringBoot 主启动类。
    - 默认服务地址：`http://localhost:8080`
    - API 文档地址 ：`http://localhost:8080/doc.html`