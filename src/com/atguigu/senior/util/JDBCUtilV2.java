package com.atguigu.senior.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * JDBC工具类 (V2.0)
 * 1.维护一个连接池和ThreadLocal
 * 2.对外提供在连接池中获取连接并绑定本线程的方法
 * 3.对外提供从线程中移除连接并回收连接的方法
 * 工具类只对外提供共性的功能代码 所以均为静态方法
 */
public class JDBCUtilV2 {
    private static DataSource dataSource;
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    static {
        try {
            Properties properties = new Properties();
            InputStream inputStream = JDBCUtil.class.getClassLoader().getResourceAsStream("db.properties");
            properties.load(inputStream);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //对外获取连接
    public static Connection getConnection() {
        try {
            Connection connection = threadLocal.get();
            if (connection == null) {
                connection = dataSource.getConnection();
                threadLocal.set(connection);
            }
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //对外回收连接
    public static void release() {
        try {
            Connection connection = threadLocal.get();
            if (connection != null) {
                threadLocal.remove();
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
