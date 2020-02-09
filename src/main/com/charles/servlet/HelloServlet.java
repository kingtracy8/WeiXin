package com.charles.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by trcay on 2020/2/8.
 */
public class HelloServlet extends HttpServlet{


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            request.setCharacterEncoding("UTF-8");

            response.setContentType("text/html; charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            String userId = request.getParameter("userId");
            String pwd = request.getParameter("pwd");
            if("admin".equals(userId) && "password".equals(pwd)){
                out.println("success");
            }else{
                out.println("fail");
            }
            out.println("<br/>");
            String servletPath = this.getServletContext().getRealPath("/");
            out.println(servletPath);
            out.close();

        } catch (Exception e) {

        }
    }

}
