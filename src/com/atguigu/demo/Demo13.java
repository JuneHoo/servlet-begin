package com.atguigu.demo;

import sun.applet.AppletIOException;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/demo13")
public class Demo13 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Object unameObj = req.getAttribute("uname");
//        Object unameObj = req.getSession().getAttribute("uname");
//        System.out.println("unameObj = " + unameObj);
        ServletContext application = req.getServletContext();
        Object unameObj = application.getAttribute("uname");
        System.out.println("unameObj = " + unameObj);
    }
}
