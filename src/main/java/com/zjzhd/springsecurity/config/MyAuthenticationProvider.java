package com.zjzhd.springsecurity.config;

import com.alibaba.fastjson.JSON;
import com.zjzhd.springsecurity.model.dto.UserDto;
import com.zjzhd.springsecurity.service.UserService;
import com.zjzhd.springsecurity.utils.JwtUtils;
import com.zjzhd.springsecurity.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author fanlz
 * @date 2021-04-14 16:37
 **/
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserService userService;
    /**
     * Performs authentication with the same contract as
     * {@link AuthenticationManager#authenticate(Authentication)}
     * .
     *
     * @param authentication the authentication request object.
     * @return a fully authenticated object including credentials. May return
     * <code>null</code> if the <code>AuthenticationProvider</code> is unable to support
     * authentication of the passed <code>Authentication</code> object. In such a case,
     * the next <code>AuthenticationProvider</code> that supports the presented
     * <code>Authentication</code> class will be tried.
     * @throws AuthenticationException if authentication fails.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取表单提交的用户名和密码
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        String md532 = Md5Util.MD5_32(password, "utf-8");
        UserDto userDetail = (UserDto) userService.loadUserByUsername(username);
        if (userDetail == null || !userDetail.getPassword().equals(md532)) {
            // 认证必须抛此异常，AuthenticationManager必须检测这个状态
            throw new BadCredentialsException("用户名不存在");
        }

        Collection<? extends GrantedAuthority> authorities = userDetail.getAuthorities();
        // 构建返回的用户登录成功的token
        // jwt令牌生成处理
        userDetail.setToken(JwtUtils.createJWT(userDetail.getId().toString(), username,
                JSON.toJSONString(authorities),
                Long.valueOf("43200000")));
        return new UsernamePasswordAuthenticationToken(userDetail, password, authorities);
    }

    /**
     * Returns <code>true</code> if this <Code>AuthenticationProvider</code> supports the
     * indicated <Code>Authentication</code> object.
     * <p>
     * Returning <code>true</code> does not guarantee an
     * <code>AuthenticationProvider</code> will be able to authenticate the presented
     * instance of the <code>Authentication</code> class. It simply indicates it can
     * support closer evaluation of it. An <code>AuthenticationProvider</code> can still
     * return <code>null</code> from the {@link #authenticate(Authentication)} method to
     * indicate another <code>AuthenticationProvider</code> should be tried.
     * </p>
     * <p>
     * Selection of an <code>AuthenticationProvider</code> capable of performing
     * authentication is conducted at runtime the <code>ProviderManager</code>.
     * </p>
     *
     * @param authentication
     * @return <code>true</code> if the implementation can more closely evaluate the
     * <code>Authentication</code> class presented
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
