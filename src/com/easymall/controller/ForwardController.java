package com.easymall.controller;


/*WEB-INF目录下的页面被保护起来,不能直接访问,需用页面跳转*/
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/forward")
public class ForwardController {
    @RequestMapping("/{path}.action") // /forward/login.action
    public String forward(@PathVariable("path") String path){
        return path; //返回 视图名称 去WEB-INF下找login.jsp
    }
}
