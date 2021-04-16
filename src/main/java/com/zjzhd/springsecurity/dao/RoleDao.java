package com.zjzhd.springsecurity.dao;

import com.zjzhd.springsecurity.model.entity.Role;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author fanlz
 * @date 2021-04-15 14:03
 **/
@Repository
public interface RoleDao {

    /**
     * 根据ID查询角色
     * @param id
     * @return
     */
    Role queryById(Long id);
}
