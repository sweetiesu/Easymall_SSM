package com.easymall.mapper;

import com.easymall.domain.User;

import java.util.List;

public interface UserMapper {

    /**
     * 查询用户
     * @param user 封装了查询条件bean
     * @return 查找到的用户beanList
     */
    List<User> queryUser(User user);

    /**
     * 新增用户
     * @param user 封装了用户信息的bean
     */
    void insertUser(User user);
}
