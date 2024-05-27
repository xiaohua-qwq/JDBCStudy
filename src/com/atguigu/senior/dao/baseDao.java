package com.atguigu.senior.dao;

import com.atguigu.senior.util.JDBCUtilV2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 工具类 用于管理通用的数据库方法
 */
public class baseDao {
    /**
     * 通用的增删改方法
     *
     * @param sql  调用者要执行的sql语句
     * @param args 传入sql语句中的参数
     * @return 受影响的行数
     */
    public Integer executeUpdate(String sql, Object... args) {
        try {
            //获取连接
            Connection connection = JDBCUtilV2.getConnection();

            //预编译sql并执行
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    preparedStatement.setObject(i + 1, args[i]);
                }
            }
            int result = preparedStatement.executeUpdate();

            //释放连接
            preparedStatement.close();
            JDBCUtilV2.release();

            //返回结果
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
