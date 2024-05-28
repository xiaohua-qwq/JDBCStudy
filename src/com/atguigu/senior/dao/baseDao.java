package com.atguigu.senior.dao;

import com.atguigu.senior.util.JDBCUtilV2;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * 通用的查询方法
     *
     * @param clazz 查询的目标数据封装类对象的字节码
     * @param sql   sql语句
     * @param args  sql语句参数
     * @param <T>   泛型
     * @return 返回查询到的结果集(只有一个数据也使用List返回)
     */
    public <T> List<T> executeQuery(Class<T> clazz, String sql, Object... args) {
        try {
            //建立连接 预编译查询语句并执行
            Connection connection = JDBCUtilV2.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    preparedStatement.setObject(i + 1, args[i]);
                }
            }
            ResultSet resultSet = preparedStatement.executeQuery();

            //获取结果集中的元数据对象 包含了列的数量和每个列的名称
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount(); //获列的数量

            //循环封装返回结果对象
            List<T> resultList = new ArrayList<>();
            while (resultSet.next()) {
                T t = clazz.newInstance(); //通过反射创建一个结果对象
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i); //获取列数据的值

                    String columnName = metaData.getColumnName(i); //获取当前列的名字
                    //TODO 数据库中列的名字和Java实体类的属性名不一致会导致报错
                    Field filLd = clazz.getDeclaredField(columnName); //获取要封装的对象属性(Field是一个属性)
                    filLd.setAccessible(false); //关闭私有化属性
                    filLd.set(t, value); //为T这个对象与当前列关联的属性赋值
                }
                resultList.add(t);
            }

            //释放链接并返回结果
            resultSet.close();
            preparedStatement.close();
            JDBCUtilV2.release();
            return resultList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
