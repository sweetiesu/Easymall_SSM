package com.easymall.controller;

import com.easymall.domain.User;
import com.easymall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService = null;

    @RequestMapping("/regist.action")
    public void regist(User user){

    }


    @RequestMapping("/validate.action")
    public void validate(){

    }


    @RequestMapping("/ajaxHasUserName.action")
    public void ajaxHasUserName(){

    }

}
