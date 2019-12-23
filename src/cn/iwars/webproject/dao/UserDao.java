package cn.iwars.webproject.dao;

import cn.iwars.webproject.entity.ShopUser;
import cn.iwars.webproject.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;

public class UserDao {

    private Connection connection = DataSourceUtils.getConnection();

    /**
     * 用户登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户实体
     * @throws SQLException 异常
     */
    public ShopUser login(String username, String password) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "select * from user where username = ? and password = ?";
        ShopUser shopUser = runner.query(connection, sql, new BeanHandler<ShopUser>(ShopUser.class), username, password);
        return shopUser;
    }

    /**
     * 用户注册
     * @param user  User
     * @return 注册结果
     * @throws SQLException  异常
     */
    public boolean save(ShopUser user) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "insert into user (?,?,?,?,?,?,?,?,?,?)";
        int save = runner.update(sql, user.getUid(), user.getUsername(), user.getPassword(), user.getName(), user.getEmail(), user.getTelephone(),
                user.getBirthday(), user.getSex(), user.getState(), user.getCode());
        if (save > 0) {
            return true;
        }
        return false;
    }
}
