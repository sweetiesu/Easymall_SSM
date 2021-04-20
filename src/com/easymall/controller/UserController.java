package com.easymall.controller;

import com.easymall.domain.User;
import com.easymall.exception.MsgException;
import com.easymall.service.UserService;
import com.easymall.utils.MD5Utils;
import com.easymall.utils.VerifyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService = null;

    @RequestMapping("/logout.action")
    public String logout(HttpServletRequest request,HttpServletResponse response,HttpSession session){
        //1.销毁session
        session.invalidate();
        //2.删除30天内自动登录cookie
        //销毁cookie
        Cookie cookie = new Cookie("autologin","");
        cookie.setPath(request.getContextPath()+"/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        //3.回到主页
        return "redirect:/index.jsp";
    }

    @RequestMapping("/login.action")
    public String login(String username, String password, String remname,String autologin, HttpSession session, Model model, HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        //0.解决乱码
        //2.获取请求参数
        //3.处理记住用户名
        if("true".equals(remname)){//勾选了记住用户名，则发送cookie保存用户名30天
            Cookie cookie = new Cookie("remname", URLEncoder.encode(username,"utf-8"));
            cookie.setPath(request.getContextPath()+"/");
            cookie.setMaxAge(60*60*24*30);//cookie保留30天
            response.addCookie(cookie);
        }else{//没有勾选了记住用户名，则删除记住用户名cookie
            Cookie cookie = new Cookie("remname","");
            cookie.setPath(request.getContextPath()+"/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        //4.处理30天内自动登录
        if("true".equals(autologin)){
            Cookie cookie = new Cookie("autologin",URLEncoder.encode(username,"utf-8")+"#"+ MD5Utils.md5(password));
            cookie.setMaxAge(60*60*24*30);//30天
            cookie.setPath(request.getContextPath()+"/");
            response.addCookie(cookie);
        }
        //5.处理用户登录逻辑
        try{
            User user = userService.login(username,MD5Utils.md5(password));
            session.setAttribute("user",user);
            return "redirect:/index.jsp";
        }catch (MsgException e){
            model.addAttribute("msg",e.getMessage());
            return "forward:/WEB-INF/jsp/login.jsp";
        }
    }

    @RequestMapping("/regist.action")
    public String regist(@Valid User user, Errors errs, String valistr, HttpSession session, Model model) {
        //0.解决乱码
        //1.获取数据封装数据
        //2.校验数据
        if(errs.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> allErros = errs.getFieldErrors();
            for(FieldError err:allErros){
                String field = err.getField();
                String msg = err.getDefaultMessage();
                System.out.println(msg+"######");
                sb.append(msg+"..");
            }
            model.addAttribute("msg",sb.toString());
            return "forward:/WEB-INF/jsp/regist.jsp";
        }
        //3.校验验证码
        String valistr2 = (String) session.getAttribute("valistr");
        if( valistr == null
                || valistr2 == null
                || !valistr.equals(valistr2)){
            model.addAttribute("msg", "验证码不正确！");
            return "forward:/WEB-INF/jsp/regist.jsp";
        }
        //4.调用Service完成逻辑
        user.setPassword(MD5Utils.md5(user.getPassword()));
        try {
            userService.reigst(user);
        } catch (MsgException e) {
            //5.注册失败，返回注册页面提示信息
            model.addAttribute("msg", e.getMessage());
            return "forward:/WEB-INF/jsp/regist.jsp";
        }
        //5.注册成功返回主页
        return "redirect:/index.jsp";
    }

    @RequestMapping("/valiImg.action")
    public void valiImg(HttpServletResponse resp, HttpSession session) throws IOException {
        //1.禁止缓存验证码图片
        resp.setDateHeader("Expires", -1);
        resp.setHeader("pragma", "no-cache");
        resp.setHeader("cache-control", "no-cache");
        //2.通过工具类绘制图片
        VerifyCode vc = new VerifyCode();
        vc.drawImage(resp.getOutputStream());
        //3.将验证码的值存入session
        String code = vc.getCode();
        session.setAttribute("valistr",code);
    }

    @ResponseBody
    @RequestMapping(value = "/ajaxHasUserName.action", produces = "text/html;charset=utf-8")
    public String ajaxHasUserName(String username) {
        //resp.setContentType("text/html;charset=utf-8");
        if (userService.hasUserName(username)) {
            return "用户名已存在";
        } else {
            return "用户名可用";
        }
    }

}
