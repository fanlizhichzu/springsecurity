package com.zjzhd.springsecurity.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjzhd.springsecurity.dao.RoleDao;
import com.zjzhd.springsecurity.dao.UserDao;
import com.zjzhd.springsecurity.model.dto.UserDto;
import com.zjzhd.springsecurity.model.entity.Role;
import com.zjzhd.springsecurity.model.entity.User;
import com.zjzhd.springsecurity.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fanlz
 * @date 2021-04-14 13:18
 **/
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.selectByName(username);

        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user,userDto);

//        userDto.setRoleIds(userDao.getRoleName(userDto.getRoleIds()));

        // 查询角色权限列表
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String roleId : userDto.getRoleIds().split(",")) {

            Role role = roleDao.queryById(Long.valueOf(roleId));
            if (role != null && role.getRole() != null) {
                GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getRole().toUpperCase());
                authorities.add(authority);
            }
        }

        userDto.setAuthorities(authorities);

        return userDto;
    }

}
