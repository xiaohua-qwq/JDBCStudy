package com.atguigu.senior;

import com.atguigu.senior.util.JDBCUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCUtilTest {
    @Test
    public void TestGetConnection() throws Exception {
        Connection connection = JDBCUtil.getConnection();
        System.out.println(connection);
        String sql = "select emp_name from t_emp where emp_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, 1);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String empName = resultSet.getString("emp_name");
            System.out.println(empName);
        }
        JDBCUtil.release(connection);
    }
}
