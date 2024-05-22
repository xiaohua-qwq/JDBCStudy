package com.atguigu.advanced;

import com.atguigu.advanced.pojo.Employee;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCAdvanced {
    @Test
    public void testORM() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/atguigu";
        Connection connection = DriverManager.getConnection(url, "root", "123456");

        String sql = "select * from t_emp where emp_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, 1);
        ResultSet resultSet = preparedStatement.executeQuery();

        Employee employee = null;

        if (resultSet.next()) {
            employee = new Employee();
            Integer empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            Double empSalary = resultSet.getDouble("emp_salary");
            Integer empAge = resultSet.getInt("emp_age");

            employee.setEmpId(empId);
            employee.setEmpName(empName);
            employee.setEmpSalary(empSalary);
            employee.setEmpAge(empAge);
        }

        System.out.println(employee);

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testORMList() throws Exception {
        String url = "jdbc:mysql://localhost:3306/atguigu";
        Connection connection = DriverManager.getConnection(url, "root", "123456");
        PreparedStatement preparedStatement = connection.prepareStatement("select * from t_emp");
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Employee> employeesList = new ArrayList<>();
        while (resultSet.next()) {
            Integer empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            Double empSalary = resultSet.getDouble("emp_salary");
            Integer empAge = resultSet.getInt("emp_age");

            Employee employee = new Employee();
            employee.setEmpId(empId);
            employee.setEmpName(empName);
            employee.setEmpSalary(empSalary);
            employee.setEmpAge(empAge);

            employeesList.add(employee);
        }

        for (Employee employee : employeesList) {
            System.out.println(employee);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testReturnPK() throws Exception {
        //创建连接
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/atguigu";
        Connection connection = DriverManager.getConnection(url, "root", "123456");

        //预编译SQL 要求数据库返回生成的主键值
        String sql = "INSERT INTO t_emp(emp_name, emp_salary, emp_age) values (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        //在占位符上填充插入的值
        Employee employee = new Employee(null, "jack", 123.45, 29);
        preparedStatement.setString(1, employee.getEmpName());
        preparedStatement.setDouble(2, employee.getEmpSalary());
        preparedStatement.setInt(3, employee.getEmpAge());

        //执行sql
        int result = preparedStatement.executeUpdate();
        ResultSet resultSet = null;

        if (result > 0) {
            //添加数据成功
            //获取新增的数据的主键值 把他填入到Java对象中
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Integer PrimaryKey = resultSet.getInt(1);
                employee.setEmpId(PrimaryKey);
            }
            System.out.println(employee);
        } else {
            throw new SQLException("添加数据失败");
        }

        if (resultSet != null) {
            resultSet.close();
        }
        preparedStatement.close();
        connection.close();
    }
}
