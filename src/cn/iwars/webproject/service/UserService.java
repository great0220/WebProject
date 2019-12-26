package cn.iwars.webproject.service;



import cn.iwars.webproject.dao.UserDao;
import cn.iwars.webproject.entity.ShopUser;
import cn.iwars.webproject.utils.MailUtils;

import java.sql.SQLException;

public class UserService {


    public ShopUser login(String username, String password) {
        ShopUser shopUser = null;
        UserDao userDao = new UserDao();
        try {
            shopUser = userDao.login(username, password);
        } catch (SQLException e) {
            System.out.println("用户登录查询数据库失败");
        }
        return shopUser;
    }

    public boolean saveUser(ShopUser user) {
        UserDao userDao = new UserDao();
        boolean save =false;
        try {
            save = userDao.save(user);
            //send mail
            String message = "请点击此链接激活: " + "http://localhost:8080/WebProject_war_exploded/userServlet?method=activeAccount&activeCode=" + user.getCode();
            MailUtils.sendMail(user.getEmail(),user.getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return save;
    }

    public Boolean checkUserName(String username) {
        UserDao userDao = new UserDao();
        ShopUser user = null;
        Boolean flag = false;
        try {
            user = userDao.getUserByUsername(username);
            if (user != null) {
                flag = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("注册校验用户名:查询数据库失败");
            return flag;
        }
        return flag;
    }

    public Boolean activeAccount(String activeCode) {
        UserDao userDao = new UserDao();
        ShopUser user = null;
        try {
            user = userDao.findShopUserByActiveCode(activeCode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (null == user) {
            return false;
        }
        int state = 1;
        try {
            userDao.updateUser(state,activeCode);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
