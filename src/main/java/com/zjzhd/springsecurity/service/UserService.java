package com.zjzhd.springsecurity.service;

import com.zjzhd.springsecurity.dao.UserDao;
import com.zjzhd.springsecurity.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author fanlz
 * @date 2021-04-13 13:51
 **/

public interface UserService extends UserDetailsService {

}
