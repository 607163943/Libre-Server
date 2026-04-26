package com.libre.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.jwt.StpLogicJwtForStateless;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import com.libre.enums.CommonExceptionEnums;
import com.libre.exception.AuthorizeException;
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
        registry.addInterceptor(new SaInterceptor(handler -> {
            // 白名单
            SaRouter.match(
                    // 管理端
                    "/admin/login/**",
                    // 用户端
                    "/user/login/**",
                    "/user/register/**",
                    "/user/search/**",
                    "/user/book/**",
                    "/user/home/top/lend/book",
                    "/user/home/top/latest/book",
                    // Knife4j文档
                    "/doc.html", "/webjars/**", "/favicon.ico", "/swagger-resources/**", "/v2/api-docs/**",
                    // 浏览器插件
                    "/.well-known/**"
            ).stop();

            // 认证
            SaRouter.match("/**").check(r -> {
                try {
                    StpUtil.checkLogin();
                } catch (Exception e) {
                    throw new LoginException(CommonExceptionEnums.LOGIN_USER_NOT_LOGIN);
                }
            });

            // 鉴权
            SaRouter.match("/admin/**").check(r -> {
                try {
                    StpUtil.checkPermissionOr("admin:client:view");
                } catch (Exception e) {
                    throw new AuthorizeException(CommonExceptionEnums.PERMISSION_DENIED);
                }
            });
        })).addPathPatterns("/**");
    }
}
