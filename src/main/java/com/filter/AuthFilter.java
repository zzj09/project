package com.filter;


import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(filterName = "AuthFilter")
public class AuthFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws ServletException, IOException {
        List<String> list=new ArrayList<>();
        list.add("index.html");
        list.add("login.html");
        list.add("register.html");
        list.add("update.html");
        list.add("login");
        list.add("register");
        list.add("update");
        //统一拦截（查询当前session是否存在user）(这里user会在每次登陆成功后，写入session)
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getRequestURL().toString();
        String $1=url.replaceFirst(".*/([^/?]+).*","$1");
        System.out.println($1);
        if(list.contains($1)){
            chain.doFilter(request, response);
            return ;
        }
        String user = (String) request.getSession().getAttribute("USER_SESSION");
        if(user ==null){
            System.out.println("未登录");
            response.sendRedirect("/login.html");
            return ;
        }
        chain.doFilter(request, response);
    }
}
