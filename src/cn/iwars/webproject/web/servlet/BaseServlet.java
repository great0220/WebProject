package cn.iwars.webproject.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


public class BaseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        String method = request.getParameter("method");
        if (method == null) {
            method = "execute";
        }
        Class<? extends BaseServlet> clazz = this.getClass();
        try {
            Constructor<? extends BaseServlet> constructor = clazz.getConstructor();
            BaseServlet servlet = constructor.newInstance();
            Method clazzMethod = clazz.getMethod(method, HttpServletRequest.class, HttpServletResponse.class);
            clazzMethod.invoke(servlet, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("reflect exception");
        }

    }

    public void execute(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("404:请求的方法不存在");
    }
}
