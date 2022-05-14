package com.controller;

import com.entity.User;
import com.service.UserService;
import com.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    @ResponseBody
    public Map<String,Object> login(HttpSession session,User user){
        Map<String,Object> map = new HashMap<>();
        boolean i = userService.query(user);
        System.out.println(user); //控制台看是否成功获取数据
        System.out.println(i);
        if(i){
            //登录成功，把信息保存到session中
            session.setAttribute("USER_SESSION",user.getUsername());
            map.put("msg","登录成功");
            map.put("status",200);
        }else{
            map.put("msg","用户名或密码错误！");
            map.put("status",500);
        }
        return map;
    }

    //@ResponseBody是将controller的方法返回的对象通过适当的转换器转换为指定
    // 的格式之后，写入到response对象的body区，通常用来返回JSON数据或者是XML数据
    @RequestMapping("/register")
    @ResponseBody
    public Map<String,Object> addUser(User user){
        Map<String,Object> map = new HashMap<>();
        System.out.println("1");
        boolean j = userService.checkUserName(user.getUsername());
        if(j){
            System.out.println("2");
            map.put("msg","用户名已存在");
        }else{
            boolean i = userService.add(user);
            if(i){
                map.put("msg","注册成功");
                map.put("status","200");
            }else{
                map.put("msg","注册失败");
                map.put("status","500");
            }
        }
        System.out.println(map);
        return map;
    }

    @RequestMapping("/update")
    @ResponseBody
    public Map<String,Object> update(UserVo userVo, HttpServletResponse response){
        Map<String,Object> map = new HashMap<>();
        User user = new User();
        user.setUsername(userVo.getUsername());
        user.setPassword(userVo.getPassword());
        boolean i = userService.update(user,userVo.getNewpassword(),userVo.getNewpassword2());
        if(i){
            map.put("msg", "修改成功");
            map.put("status", "200");
        }else{
            map.put("msg", "用户名或密码错误");
            map.put("status", "500");
        }
        return map;
    }

    //获取个人信息
    @RequestMapping("/getUser")
    @ResponseBody
    public Map<String,Object> getUser(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException{
        HttpSession session = httpServletRequest.getSession(false);
        Map<String,Object> map = new HashMap<>();
        if (session.getAttribute("USER_SESSION") != null){
            String username = (String) session.getAttribute("USER_SESSION");
            User user = userService.findUserByUserName(username);
            map.put("user",user);
            map.put("status","200");
        }else{
            //session没有用户信息重定向到登录页面
            String url = "/login.html";
            httpServletResponse.sendRedirect(url);
        }
        return map;
    }

    @RequestMapping("/loginout")
    public void loginout(HttpServletResponse httpServletResponse,HttpServletRequest httpServletRequest) throws IOException{
        HttpSession session = httpServletRequest.getSession(false);
        if (session.getAttribute("USER_SESSION") != null) {
            session.removeAttribute("USER_SESSION");
        }
        String url = "/index.html";
        httpServletResponse.sendRedirect(url);
    }



    @RequestMapping("/jump1")
    public String jump1(){
        return "model";
    }

    @RequestMapping("/jump2")
    public String jump2(){
        return "education";
    }

}
