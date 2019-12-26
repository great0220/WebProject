package cn.iwars.webproject.web.servlet;

import cn.iwars.webproject.entity.ShopUser;
import cn.iwars.webproject.service.UserService;
import cn.iwars.webproject.utils.UUIDUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;


public class UserServlet extends BaseServlet {

    /**
     * 用户登录
     *
     * @param request  请求
     * @param response 响应
     * @throws ServletException 异常
     * @throws IOException      异常
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        HttpSession session = request.getSession();
        String sessionCode = (String) session.getAttribute("checkcode_session");
        //验证码错误,转发到登录页面,并提示
        if (!code.equals(sessionCode)) {
            request.setAttribute("loginInfo", "验证码错误!请重新输入");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            UserService userService = new UserService();
            ShopUser shopUser = userService.login(username, password);
            //用户名或密码错误,转发到登录页面,并提示
            if (null == shopUser) {
                request.setAttribute("loginInfo", "用户名或密码错误!请重新输入");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                //登录成功,将用户信息写入session
                session.setAttribute("shopUser", shopUser);
                String autoLogin = request.getParameter("autoLogin");
                //自动登录
                if (null != autoLogin) {
                    Cookie username_cookie = new Cookie("username_cookie", shopUser.getUsername());
                    Cookie password_cookie = new Cookie("password_cookie", shopUser.getPassword());
                    username_cookie.setMaxAge(604800);
                    password_cookie.setMaxAge(604800);
                    response.addCookie(username_cookie);
                    response.addCookie(password_cookie);
                }
                String rememberUsername = request.getParameter("rememberUsername");
                //记住用户名
                if (null != rememberUsername) {
                    Cookie rememberUsername_cookie = new Cookie("rememberUsername_cookie", shopUser.getUsername());
                    rememberUsername_cookie.setMaxAge(604800);
                    rememberUsername_cookie.setPath("/");
                    response.addCookie(rememberUsername_cookie);
                }
                response.sendRedirect(request.getContextPath());
            }

        }

    }

    /**
     * 用户注册
     *
     * @param request  请求
     * @param response 响应
     * @throws ServletException 异常
     * @throws IOException      异常
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("checkCode");
        HttpSession session = request.getSession();
        String sessionCode = (String) session.getAttribute("checkcode_session");
        if (code == null || !code.equals(sessionCode)) {
            request.setAttribute("registerInfo", "验证码错误!请重新输入");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        Map<String, String[]> userMap = request.getParameterMap();
        ShopUser user = new ShopUser();
        try {
            BeanUtils.populate(user, userMap);
            user.setState(0);
            user.setCode(UUIDUtils.getUUId());
            user.setUid(UUIDUtils.getUUId());
            UserService userService = new UserService();
            boolean isSuccess = userService.saveUser(user);
            if (isSuccess) {
                response.sendRedirect(request.getContextPath() + "/registerSuccess.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/registerFailed.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("register service swap bean failed");
        }
    }

    /**
     * 检验用户名是否存在
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void checkUserName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        if ("" != username) {
            UserService userService = new UserService();
            Boolean result = userService.checkUserName(username);
            if (result) {
                response.getWriter().write(1);
            } else {
                response.getWriter().write(0);
            }
        }
    }

    /**
     * 退出登录
     *
     * @param request
     * @param response
     */
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute("shopUser");
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                Cookie username_cookie = new Cookie("username_cookie", "");
                Cookie password_cookie = new Cookie("password_cookie", "");
                username_cookie.setMaxAge(0);
                password_cookie.setMaxAge(0);
                response.addCookie(username_cookie);
                response.addCookie(password_cookie);
            }
        }
        try {
            response.sendRedirect(request.getContextPath());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("退出登录,重定向到首页失败!");
        }
    }

    public void activeAccount(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String activeCode = request.getParameter("activeCode");
        UserService userService = new UserService();

        Boolean result = userService.activeAccount(activeCode);
        if (result) {
            request.setAttribute("activeMsg","激活成功,请登录");
        }else {
            request.setAttribute("activeMsg","激活失败,请联系管理员");
        }
        request.getRequestDispatcher("active.jsp").forward(request,response);
    }


}
