package com.atguigu.senior;

import com.atguigu.senior.dao.BankDao;
import com.atguigu.senior.dao.EmployeeDao;
import com.atguigu.senior.dao.baseDao;
import com.atguigu.senior.dao.EmployeeDao;
import com.atguigu.senior.dao.impl.BankDaoImpl;
import com.atguigu.senior.dao.impl.employeeDaoImpl;
import com.atguigu.senior.pojo.Employee;
import com.atguigu.senior.util.JDBCUtil;
import com.atguigu.senior.util.JDBCUtilV2;
import org.junit.Test;

import java.sql.*;
import java.util.List;

public class JDBCUtilTest {
    //测试JDBC中查询的代码
    private void testJDBC(Connection connection) {
        try {
            System.out.println(connection);
            String sql = "select emp_name from t_emp where emp_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 1);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String empName = resultSet.getString("emp_name");
                System.out.println(empName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetConnection() throws Exception {
        Connection connection = JDBCUtil.getConnection();
        testJDBC(connection);
        JDBCUtil.release(connection);
    }

    @Test
    public void testJDBCUtilV2() throws Exception {
        Connection connection = JDBCUtilV2.getConnection();
        testJDBC(connection);
        JDBCUtilV2.release();
    }

    @Test
    public void testJDBCUtilV1AndV2() {
        //v1工具类(没有使用ThreadLocal)
        Connection v1Connection1 = JDBCUtil.getConnection();
        Connection v1Connection2 = JDBCUtil.getConnection();
        Connection v1Connection3 = JDBCUtil.getConnection();
        System.out.println(v1Connection1);
        System.out.println(v1Connection2);
        System.out.println(v1Connection3);
        JDBCUtil.release(v1Connection1);
        JDBCUtil.release(v1Connection2);
        JDBCUtil.release(v1Connection3);

        //v2工具类(使用ThreadLocal)
        Connection v2Connection1 = JDBCUtilV2.getConnection();
        Connection v2Connection2 = JDBCUtilV2.getConnection();
        Connection v2Connection3 = JDBCUtilV2.getConnection();
        System.out.println(v2Connection1);
        System.out.println(v2Connection2);
        System.out.println(v2Connection3);
        JDBCUtilV2.release();
    }

    @Test
    public void testPublicQuery() throws Exception {
        EmployeeDao employeeDao = new employeeDaoImpl();
        List<Employee> employees = employeeDao.selectAll();
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    @Test
    public void whatIsThisColumnFuck() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/atguigu", "root", "123456");
        String sql = "select emp_id as empId,emp_name as empName,emp_salary as empSalary,emp_age as empAge from t_emp";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            ResultSetMetaData metaData = preparedStatement.getMetaData();
            String columnName1 = metaData.getColumnName(1);
            String columnName2 = metaData.getColumnName(2);
            String columnName3 = metaData.getColumnName(3);
            String columnName4 = metaData.getColumnName(4);
            System.out.println(columnName1 + columnName2 + columnName3 + columnName4);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testTransaction() {
        BankDao bankDao = new BankDaoImpl();
        Connection connection = null;
        try {
            //获取连接并关闭自动提交事务
            connection = JDBCUtilV2.getConnection();
            connection.setAutoCommit(false);

            //设置扣款和加款
            bankDao.subMoney(1, 100);
            bankDao.addMoney(2, 100);

            connection.commit(); //提交事务
        } catch (SQLException e) {
            try {
                connection.rollback(); //执行事务回滚
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            JDBCUtilV2.release();
        }
    }
}
