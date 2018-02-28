package com.imooc.miaosha.domain;

import java.util.Date;

/**
 * miaosha_user数据库映射实体
 */
public class MiaoshaUser {
    /**
     * 用户ID，手机号码
     */
    private Long id;

    private String nickname;

    /**
     * MD5(MD5(pass明文+固定salt) + salt)
     */
    private String password;

    private String salt;

    /**
     * 头像，云存储的ID
     */
    private String head;

    /**
     * 注册时间
     */
    private Date registerDate;

    /**
     * 上蔟登录时间
     */
    private Date lastLoginDate;

    /**
     * 登录次数
     */
    private Integer loginCount;

    /**
     * 获取用户ID，手机号码
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置用户ID，手机号码
     */
    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取MD5(MD5(pass明文+固定salt) + salt)
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置MD5(MD5(pass明文+固定salt) + salt)
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 获取头像，云存储的ID
     */
    public String getHead() {
        return head;
    }

    /**
     * 设置头像，云存储的ID
     */
    public void setHead(String head) {
        this.head = head;
    }

    /**
     * 获取注册时间
     */
    public Date getRegisterDate() {
        return registerDate;
    }

    /**
     * 设置注册时间
     */
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    /**
     * 获取上蔟登录时间
     */
    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    /**
     * 设置上蔟登录时间
     */
    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    /**
     * 获取登录次数
     */
    public Integer getLoginCount() {
        return loginCount;
    }

    /**
     * 设置登录次数
     */
    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }
}