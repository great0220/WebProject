package cn.iwars.webproject.web.servlet;

import cn.iwars.webproject.entity.ShopUser;
import cn.iwars.webproject.service.UserService;
import cn.iwars.webproject.utils.UUIDUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;


public class UserServlet extends BaseServlet {

    /**
     * 用户登录
     * @param request   请求
     * @param response 响应
     * @throws ServletException  异常
     * @throws IOException   异常
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        HttpSession session = request.getSession();
        String sessionCode = (String) session.getAttribute("checkcode_session");
        //验证码错误,转发到登录页面,并提示
        if (!code.equals(sessionCode)) {
            request.setAttribute("loginInfo","验证码错误!请重新输入");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        } else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            UserService userService = new UserService();
            ShopUser shopUser = userService.login(username, password);
            //用户名或密码错误,转发到登录页面,并提示
            if (null == shopUser) {
                request.setAttribute("loginInfo", "用户名或密码错误!请重新输入");
                request.getRequestDispatcher("/login.jsp").forward(request,response);
            }else {
                //登录成功,将用户信息写入session
                session.setAttribute("shopUser",shopUser);
                response.sendRedirect(request.getContextPath());
            }

        }

    }

    /**
     * 用户注册
     * @param request   请求
     * @param response 响应
     * @throws ServletException  异常
     * @throws IOException   异常
     */
    public void resgister(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        HttpSession session = request.getSession();
        String sessionCode = (String) session.getAttribute("checkcode_session");
        if (!code.equals(sessionCode)) {
            request.setAttribute("registerInfo","验证码错误!请重新输入");
            request.getRequestDispatcher("/register.jsp").forward(request,response);
            return;
        }
        Map<String, String[]> userMap = request.getParameterMap();
        ShopUser user = new ShopUser();
        try {
            BeanUtils.populate(user,userMap);
            user.setState(0);
            user.setCode(UUIDUtils.getUUId());
            user.setUid(UUIDUtils.getUUId());
            UserService userService = new UserService();
            boolean isSuccess = userService.saveUser(user);
            if (isSuccess) {
                response.sendRedirect(request.getContextPath()+"/registerSuccess.jsp");
            }else {
                response.sendRedirect(request.getContextPath()+"/registerFailed.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("register service swap bean failed");
        }
    }



}
