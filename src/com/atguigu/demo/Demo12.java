package com.atguigu.demo;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/demo12")
public class Demo12 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 演示Application 的保存作用域（一个应用程序内）（一个Servlet只有一个实例）
        // 换个浏览器一样可以收到 lili
        ServletContext application = req.getServletContext();
        application.setAttribute("uname", "lili");
        // 客户端重定向
        resp.sendRedirect("demo13");
//        req.getRequestDispatcher("demo13").forward(req, resp);
    }
}
