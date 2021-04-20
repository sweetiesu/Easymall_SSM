package com.easymall.filter;

import com.easymall.domain.User;
import com.easymall.service.UserService;
import com.easymall.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

public class AutoLoginFilter implements Filter {

    private ApplicationContext context = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext sc = filterConfig.getServletContext();
        context = WebApplicationContextUtils.getWebApplicationContext(sc);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        //1.检查用户是否登录
        User user = (User) req.getSession().getAttribute("user");
        if(user==null){//未登录
            //2.检查用户是否携带了自动登录cookie
            Cookie findC = null;
            Cookie[] cs = req.getCookies();
            if(cs!=null){
                for(Cookie c : cs){
                    if("autologin".equals(c.getName())){
                        findC = c;
                        break;
                    }
                }
            }
            if(findC!=null){//带了自动登录cookie
                //3.检查携带的自动登录cookie中的用户名密码是否正确
                String v = findC.getValue();
                String username = URLDecoder.decode(v.split("#")[0],"utf-8");
                String MD5Psw = v.split("#")[1];
                UserService userService = context.getBean(UserService.class);
                User findUser = userService.login(username, MD5Psw);
                if(findUser!=null){//用户名密码是否正确
                    //4.未登录 存在自动登录cookie 且自动登录cookie中的用户名密码正确，自动登录
                    req.getSession().setAttribute("user",findUser);
                }
            }
        }

        //5.无论是否自动登录，都放行资源
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
