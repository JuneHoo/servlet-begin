package com.atguigu.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/demo10")
public class Demo10 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 演示req、session的保存作用域（一次请求 一个session内）
        req.setAttribute("uname", "aaa");
        // 客户端重定向
//        resp.sendRedirect("demo11");
        req.getRequestDispatcher("demo11").forward(req, resp);
    }
}
