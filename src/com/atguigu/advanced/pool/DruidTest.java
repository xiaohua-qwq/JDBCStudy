package com.atguigu.advanced.pool;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class DruidTest {
    @Test
    public void testHardCodeDruid() throws Exception {
        //1.获取druidDataSource
        DruidDataSource druidDataSource = new DruidDataSource();

        //2.配置连接池信息
        //2.1必须配置的设置 驱动类信息 url地址 用户名 密码
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/atguigu");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("123456");

        //2.2非必须配置的信息 Init初始化时连接池中的数量 最大链接数量
        druidDataSource.setInitialSize(10);
        druidDataSource.setMaxActive(20);

        //3.从连接池获取连接
        DruidPooledConnection connection = druidDataSource.getConnection();
        System.out.println("从连接池获取到的链接信息: " + connection);
        PreparedStatement preparedStatement = connection.prepareStatement("select emp_name from t_emp");
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String empName = resultSet.getString("emp_name");
            System.out.println(empName);
        }

        //4.关闭连接(归还连接给连接池)
        connection.close();
    }

    @Test
    public void testResourcesDruid() throws Exception {
        //1.创建properties对象
        Properties properties = new Properties();

        //2.将配置文件加载的properties对象中
        InputStream inputStream = DruidTest.class.getClassLoader().getResourceAsStream("db.properties");
        properties.load(inputStream); //加载获取到的配置文件

        //3.使用properties对象创建Druid连接池
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);

        //4.获取连接
        Connection connection = dataSource.getConnection();
        System.out.println("获取到的链接信息: " + connection);

        //5.测试CRUD
        String url = "select emp_name from t_emp where emp_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(url);
        preparedStatement.setInt(1, 1);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String empName = resultSet.getString("emp_name");
            System.out.println(empName);
        }

        //6.回收连接
        connection.close();
    }
}
