package com.zjzhd.springsecurity.model.entity;

import javax.persistence.*;


/**
 * @author fanlz
 * @date 2021-04-13 13:49
 **/
@Entity(name = "t_user")
public class User{

    private static final long serialVersionUID = 6966571335580272171L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roleIds;
    private String username;
    private String password;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
