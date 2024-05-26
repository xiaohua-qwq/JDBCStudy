package com.atguigu.advanced.pool;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
}
