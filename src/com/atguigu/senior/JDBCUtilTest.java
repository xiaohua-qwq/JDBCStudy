package com.atguigu.senior;

import com.atguigu.senior.dao.baseDao;
import com.atguigu.senior.pojo.Employee;
import com.atguigu.senior.util.JDBCUtil;
import com.atguigu.senior.util.JDBCUtilV2;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        baseDao baseDao = new baseDao();
        String sql = "select * from t_emp where emp_id = ?";
        List<Employee> employees = baseDao.executeQuery(Employee.class, sql, 1);
        System.out.println(employees);
    }
}
