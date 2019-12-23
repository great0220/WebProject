package cn.iwars.webproject.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSourceUtils {
	
	private static DataSource dataSource = new ComboPooledDataSource();
	
	private static ThreadLocal<Connection> tl = new ThreadLocal<>();
	//获得数据源
	public static DataSource getDataSource() {
		return dataSource;
	}
	//获得数据库链接
	public static Connection getConnection() {
		Connection connection = tl.get();
		if(connection == null) {
			try {
				connection =  dataSource.getConnection();
				tl.set(connection);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("database connection failed!");
			}
		}
		return connection;
		
	}
	//开启事务
	public static void startTransAction() {
		Connection connection = tl.get();
		try {
			if(connection != null) {
				connection.setAutoCommit(false);
			}
		} catch (SQLException e) {
			System.out.println("start transaction failed!");
		}
		
	}
	//回滚事务
	public static void rollback() {
		Connection connection = tl.get();
		try {
			if(connection != null) {
				connection.rollback();
			}
		} catch (SQLException e) {
			System.out.println("rollback transaction failed!");
		}
	}
	//提交事务
	public static void commit() {
		Connection connection = tl.get();
		try {
			if(connection != null) {
				connection.commit();
				connection.close();
				tl.remove();   //将connection从本地线程变量中删除
			}
		} catch (SQLException e) {
			System.out.println("commit transaction failed!");
		}
	}
	//关闭conn资源
	public static void closeConnection() {
		Connection conn = getConnection();
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("close connection failed!");
			}
		}
	}
	//关闭statement资源
	public static void closeStatement(Statement st) {
	
		if(st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				System.out.println("close statement failed!");
			}
		}
	}
	//关闭resultSet资源
	public static void closeResultSet(ResultSet rs) {
		
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println("close resultSet failed!");
			}
		}
	}
	
	
}
