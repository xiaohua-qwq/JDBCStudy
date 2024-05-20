package com.atguigu.base;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCOperation {
    /**
     * 查询单行单列
     */
    @Test
    public void testQuerySingleRowAndColumn() throws Exception {
        //1.注册驱动(可以省略)
        Class.forName("com.mysql.cj.jdbc.Driver");

        //2.创建连接
        String url = "jdbc:mysql://localhost:3306/atguigu";
        Connection connection = DriverManager.getConnection(url, "root", "123456");

        //3.预编译SQL语句得到 preparedStatement 对象
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) as count FROM t_emp");

        //4.执行查询
        ResultSet resultSet = preparedStatement.executeQuery();

        //5.获取结果
        if (resultSet.next()) {
            int count = resultSet.getInt("count");
            System.out.println(count);
        }

        //6.关闭连接
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    /**
     * 查询单行多列
     */
    @Test
    public void testQuerySingleRow() throws Exception {
        //1.注册驱动
        //2.获取连接对象
        String url = "jdbc:mysql://localhost:3306/atguigu";
        Connection connection = DriverManager.getConnection(url, "root", "123456");

        //3.预编译SQL
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM t_emp WHERE emp_id = ?");

        //4.为占位符赋值并执行SQL并接受结果
        preparedStatement.setInt(1, 5);
        ResultSet resultSet = preparedStatement.executeQuery();

        //5.处理结果
        while (resultSet.next()) {
            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            double empSalary = resultSet.getDouble("emp_salary");
            int empAge = resultSet.getInt("emp_age");

            System.out.println(empId + "\t" + empName + "\t" + empSalary + "\t" + empAge);
        }

        //6.关闭连接
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    /**
     * 查询多行多列
     */
    @Test
    public void testQueryMoreRow() throws Exception {
        String url = "jdbc:mysql://localhost:3306/atguigu";
        Connection connection = DriverManager.getConnection(url, "root", "123456");

        String sql = "SELECT * FROM t_emp WHERE emp_age > ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, 25);
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

    /**
     * 执行新增
     */
    @Test
    public void testInsert() throws Exception {
        String url = "jdbc:mysql://localhost:3306/atguigu";
        Connection connection = DriverManager.getConnection(url, "root", "123456");

        String sql = "INSERT INTO t_emp(emp_name, emp_salary, emp_age) VALUES(?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, "rose");
        preparedStatement.setDouble(2, 345.67);
        preparedStatement.setInt(3, 28);

        //新增数据和修改数据用的都是executeUpdate
        int result = preparedStatement.executeUpdate();

        //如果返回的受影响行数大于零行 代表执行成功
        if (result > 0) {
            System.out.println("新增成功 影响了" + result + "行");
        } else System.out.println("新增失败");

        preparedStatement.close();
        connection.close();
    }

    /**
     * 执行修改
     */
    @Test
    public void testUpdate() throws Exception{
        String url = "jdbc:mysql://localhost:3306/atguigu";
        Connection connection = DriverManager.getConnection(url, "root", "123456");

        String sql = "UPDATE t_emp SET emp_salary = ? WHERE emp_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setDouble(1, 888.88);
        preparedStatement.setInt(2, 6);

        //新增数据和修改数据用的都是executeUpdate
        int result = preparedStatement.executeUpdate();

        //如果返回的受影响行数大于零行 代表执行成功
        if (result > 0) {
            System.out.println("更新数据成功 影响了" + result + "行");
        } else System.out.println("更新数据失败");

        preparedStatement.close();
        connection.close();
    }

    /**
     * 执行删除
     */
    @Test
    public void testDelete() throws Exception {
        String url = "jdbc:mysql://localhost:3306/atguigu";
        Connection connection = DriverManager.getConnection(url, "root", "123456");

        String sql = "DELETE FROM t_emp WHERE emp_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, 6);

        //新增数据和修改数据用的都是executeUpdate
        int result = preparedStatement.executeUpdate();

        //如果返回的受影响行数大于零行 代表执行成功
        if (result > 0) {
            System.out.println("删除数据成功 影响了" + result + "行");
        } else System.out.println("删除数据失败");

        preparedStatement.close();
        connection.close();
    }
}
