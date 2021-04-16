package com.zjzhd.springsecurity;

import com.alibaba.fastjson.JSON;
import com.zjzhd.springsecurity.dao.UserDao;
import com.zjzhd.springsecurity.model.entity.Role;
import com.zjzhd.springsecurity.model.entity.User;
import com.zjzhd.springsecurity.service.UserService;
import com.zjzhd.springsecurity.utils.Md5Util;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@MapperScan(basePackages = {"com.zjzhd.springsecurity.dao"})
class SpringSecurityApplicationTests {

    @Resource
    UserDao userDao;

    @Test
    void contextLoads() {
        System.out.println("danyuanceshi ");

        User user = null;
        user = userDao.selectByName("fanlz");

        System.out.println(JSON.toJSON(user));

    }

}
