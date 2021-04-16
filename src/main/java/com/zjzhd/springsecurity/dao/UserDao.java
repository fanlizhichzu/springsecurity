package com.zjzhd.springsecurity.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjzhd.springsecurity.model.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * @author fanlz
 * @date 2021-04-13 13:52
 **/
@Repository
public interface UserDao extends BaseMapper<User> {

    User selectByName(@Param("username") String username);

    /**
     * 根据角色ID获取角色名称
     * @param rid
     * @return
     */
    String getRoleName(@Param("role_ids")String rid);
}
