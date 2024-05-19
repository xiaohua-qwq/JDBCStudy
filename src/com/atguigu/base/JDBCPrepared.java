package com.atguigu.base;

import java.sql.*;
import java.util.Scanner;

public class JDBCPrepared {
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/atguigu";
        String userName = "root";
        String password = "123456";
        Connection connection = DriverManager.getConnection(url, userName, password);

        //创建prepareStatement对象(防止依赖注入)
        String sql = "select * from t_emp where emp_name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //输入要查询的员工姓名
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要查询的员工姓名: ");
        String name = scanner.nextLine();

        //为?占位符赋值 然后执行SQL 返回结果
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            double empSalary = resultSet.getDouble("emp_salary");
            int empAge = resultSet.getInt("emp_age");
            System.out.println(empId + "\t" + empName + "\t" + empSalary + "\t" + empAge);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

    }
}
