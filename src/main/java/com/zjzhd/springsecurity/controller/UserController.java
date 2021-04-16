package com.zjzhd.springsecurity.controller;

import com.zjzhd.springsecurity.service.UserService;
import com.zjzhd.springsecurity.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author fanlz
 * @date 2021-04-14 13:32
 **/
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getUser/{countryName}")
    public UserDetails getUser(@PathVariable String countryName){
        return userService.loadUserByUsername(countryName);
    }

}
