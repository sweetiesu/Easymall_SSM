package com.easymall.service;

import com.easymall.domain.User;
import com.easymall.exception.MsgException;

public interface UserService {
    /**
     * 注册用户
     * @param user 封装了用户信息的bean
     */
    void reigst(User user);

    /**
     * 检查用户名是否已经存在
     * @param username 要检查的用户名
     * @return 存在则true，不存在则false
     */
    boolean hasUserName(String username);

    /**
     * 登录用户
     * @param username 要登录的用户名
     * @param password 用户登录的密码
     */
    User login(String username, String password);
}
