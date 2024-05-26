package com.atguigu.advanced.pool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class HikariTest {
    @Test
    public void testHardCodeHikari() throws Exception {
        //1.创建连接池对象
        HikariDataSource hikariDataSource = new HikariDataSource();

        //2.配置连接池信息
        //2.1必须配置
        hikariDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/atguigu");
        hikariDataSource.setUsername("root");
        hikariDataSource.setPassword("123456");

        //2.2非必须配置
        hikariDataSource.setMinimumIdle(10);
        hikariDataSource.setMaximumPoolSize(20);

        //3.通过连接池获取连接对象
        Connection connection = hikariDataSource.getConnection();
        System.out.println(connection);
        String sql = "select emp_name from t_emp where emp_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, 1);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String empName = resultSet.getString("emp_name");
            System.out.println(empName);
        }
        resultSet.close();
        preparedStatement.close();

        //4.回收连接
        connection.close();
    }

    @Test
    public void testResourcesHikari() throws Exception {
        //1.创建properties对象
        Properties properties = new Properties();

        //2.通过类加载器读取配置文件中的信息到properties对象中
        InputStream inputStream = HikariTest.class.getClassLoader().getResourceAsStream("hikari.properties");
        properties.load(inputStream); //properties对象加载读取到的配置文件

        //3.创建hikariConfig对象并传入properties对象
        HikariConfig hikariConfig = new HikariConfig(properties);

        //4.通过hikariConfig创建一个hikariDataSource连接池对象
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);

        //5.获取连接
        Connection connection = hikariDataSource.getConnection();
        System.out.println(connection);
        String sql = "select emp_name from t_emp where emp_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, 1);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String empName = resultSet.getString("emp_name");
            System.out.println(empName);
        }
        resultSet.close();
        preparedStatement.close();

        //6.回收连接
        connection.close();
    }

    @Test
    public void testResourcesHikariQwQ() throws Exception {
        Properties properties = new Properties();
        InputStream inputStream = HikariTest.class.getClassLoader().getResourceAsStream("hikari.properties");
        properties.load(inputStream);

        HikariConfig hikariConfig = new HikariConfig(properties);
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        Connection connection = hikariDataSource.getConnection();
        String sql = "select emp_name from t_emp where emp_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, 1);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String empName = resultSet.getString("emp_name");
            System.out.println(empName);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
}
