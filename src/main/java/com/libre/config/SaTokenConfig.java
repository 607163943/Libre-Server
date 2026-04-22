package com.libre.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.jwt.StpLogicJwtForStateless;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.LoginException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfig implements WebMvcConfigurer {
    // Sa-Token 整合 jwt (Stateless 无状态模式)
    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForStateless();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，定义详细认证规则
        registry.addInterceptor(new SaInterceptor(handler -> {
            // 指定一条 match 规则
            SaRouter
                    .match("/**")    // 拦截的 path 列表，可以写多个 */
                    // 管理端
                    .notMatch("/admin/login", "/admin/login/**")
                    // 用户端
                    .notMatch("/login", "/login/**")
                    .notMatch("/register", "/register/**")// 排除掉的 path 列表，可以写多个
                    .notMatch("/search", "/search/**")
                    .notMatch("/book", "/book/**")
                    // 放行Knife4j文档
                    .notMatch("/doc.html", "/webjars/**", "/favicon.ico", "/swagger-resources/**", "/v2/api-docs/**")
                    // 放行浏览器插件请求
                    .notMatch("/.well-known/**")
                    // 放行用户端首页接口
                    .notMatch("/home/top/lend/book", "/home/top/latest/book")
                    .check(r -> {
                        try {
                            StpUtil.checkLogin();
                        } catch (Exception e) {
                            throw new LoginException(ExceptionEnums.LOGIN_USER_NOT_LOGIN);
                        }
                    });        // 要执行的校验动作，可以写完整的 lambda 表达式
        })).addPathPatterns("/**");
    }
}
