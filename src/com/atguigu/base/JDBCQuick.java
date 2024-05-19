package com.atguigu.base;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCQuick {
    public static void main(String[] args) throws Exception {
        // 1.注册驱动(JDK6之后可以省略)
        //Class.forName("com.mysql.cj.jdbc.Driver");
        //这种注册方法和com.mysql.cj.jdbc.Driver中的注册方法相同
        //new的Driver要是com.mysql.cj.jdbc包下的
        //DriverManager.registerDriver(new Driver());

        // 2.创建链接对象
        String url = "jdbc:mysql://localhost:3306/atguigu";
        String userName = "root";
        String password = "123456";
        Connection connection = DriverManager.getConnection(url, userName, password);

        // 3.获取执行sql语句的对象
        Statement statement = connection.createStatement();

        // 4.执行sql语句
        String sql = "SELECT * FROM t_emp";
        ResultSet resultSet = statement.executeQuery(sql); //得到返回的结果集

        // 5.遍历结果集
        while (resultSet.next()) {
            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            double empSalary = resultSet.getDouble("emp_salary");
            int empAge = resultSet.getInt("emp_age");
            System.out.println(empId + "\t" + empName + "\t" + empSalary + "\t" + empAge);
        }

        // 6.关闭链接(先开后关)
        resultSet.close();
        statement.close();
        connection.close();
    }
}
