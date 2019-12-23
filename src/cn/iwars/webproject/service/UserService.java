package cn.iwars.webproject.service;



import cn.iwars.webproject.dao.UserDao;
import cn.iwars.webproject.entity.ShopUser;

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
        } catch (SQLException e) {
            return false;
        }
        return save;
    }
}
