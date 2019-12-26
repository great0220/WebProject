package cn.iwars.webproject.web.filter;

import cn.iwars.webproject.entity.ShopUser;
import cn.iwars.webproject.service.UserService;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AutoLoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("auto login filter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        ShopUser user = (ShopUser) request.getSession().getAttribute("shopUser");
        if (null == user) {
            Cookie[] cookies = request.getCookies();
            if (null != cookies) {
                String username = null;
                String password = null;
                for (Cookie cookie : cookies) {
                    if ("username_cookie".equals(cookie.getName()) ) {
                        username = cookie.getValue();
                    }
                    if ("password_cookie".equals(cookie.getName()) ) {
                        password = cookie.getValue();
                    }
                }
                if (null != username && null != password) {
                    UserService userService = new UserService();
                    ShopUser shopUser = userService.login(username, password);
                    if (null != shopUser) {
                        request.getSession().setAttribute("shopUser",shopUser);

                    }
                }
            }
        }
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
