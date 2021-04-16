package com.zjzhd.springsecurity.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.web.cors.CorsUtils;

import javax.annotation.Resource;
import java.io.PrintWriter;

/**
 * Security peizhilei
 *
 * @author fanlz
 * @date 2021-04-12 10:29
 **/
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    MyAuthenticationProvider myAuthenticationProvider;


    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return hierarchy;
    }

    /**
     * 用户认证
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(myAuthenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**","/getUser/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().authorizeRequests()
                // 对preFlight放行
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//                .antMatchers("/login").permitAll()
                // 设置不被拦截的路径
                //.antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                // 开启登录,loginProcessingUrl用于指定前后端分离的时候调用后台登录接口的名称
                .and().formLogin().loginProcessingUrl("/login")
                .successHandler((req, resp, authentication) -> {
                    Object principal = authentication.getPrincipal();
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(principal));
                    out.flush();
                    out.close();
                })
//                // 自定义登录成功处理器
//                .successHandler(successHandler)
//                // 自定义登录失败处理器
//                .failureHandler(failHandler)
                // 开启注销,logoutUrl用于指定前后端分离的时候调用后台注销接口的名称
                .and().logout().logoutUrl("/logout")
                // 注销成功处理器
//                .logoutSuccessHandler(logoutSuccessHandler)
                // 关闭使用HTTP基于验证进行认证
                .and()
                //.httpBasic().disable()
                .csrf().disable();
        // 自定义未登录返回信息
//        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
    }
}
