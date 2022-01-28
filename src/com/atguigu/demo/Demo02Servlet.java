package com.atguigu.demo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Demo02Servlet extends HttpServlet {
    // Servlet是单例的：所有请求都是一个实例去响应
    // 线程不安全：尽量不要在 Servlet中定义成员变量
    //
//    public void Demo02Servlet() {
//        System.out.println("实例化");
//    }

    @Override
    public void init() throws ServletException {
        System.out.println("正在初始化");
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("正在服务");
    }

    @Override
    public void destroy() {
        System.out.println("正在销毁");
    }
}
