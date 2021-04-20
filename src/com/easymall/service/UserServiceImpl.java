package com.easymall.service;

import com.easymall.domain.User;
import com.easymall.exception.MsgException;
import com.easymall.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper = null;

    @Transactional
    @Override
    public void reigst(User user) {
        //1.检查用户名是否存在
        List<User> list = userMapper.queryUser(new User(0,user.getUsername(),null,null,null));
        //2.如果存在，抛出异常
        if(list.size() != 0){
            throw new MsgException("用户名已存在！");
        }else{
            //3.如果不存在，则注册用户
            userMapper.insertUser(user);
        }
    }

    @Override
    public boolean hasUserName(String username) {
        //1.根据用户名查找数据库中的用户
        List<User> list = userMapper.queryUser(new User(0,username,null,null,null));
        //2.返回是否找到
        return list.size() != 0;
    }

    @Override
    public User login(String username, String password) {
        List<User> list = userMapper.queryUser(new User(0,username,password,null,null));
        if(list.size()==0){
            throw new MsgException("用户名密码不正确！");
        }else{
            return list.get(0);
        }
    }

}
