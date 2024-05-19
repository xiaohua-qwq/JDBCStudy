package com.atguigu.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCInjection {
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/atguigu";
        String userName = "root";
        String password = "123456";
        Connection connection = DriverManager.getConnection(url, userName, password);

        Statement statement = connection.createStatement();

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要查询的员工姓名: ");
        String name = scanner.nextLine();

        String sql = "select * from t_emp where emp_name = '" + name + "'";
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            double empSalary = resultSet.getDouble("emp_salary");
            int empAge = resultSet.getInt("emp_age");
            System.out.println(empId + "\t" + empName + "\t" + empSalary + "\t" + empAge);
        }

        resultSet.close();
        statement.close();
        connection.close();

    }
}
