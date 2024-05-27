package com.atguigu.senior.dao;

import com.atguigu.senior.pojo.Employee;

import java.util.List;

public interface employeeDao {

    /**
     * 查询数据库中所有的记录
     *
     * @return 所有记录
     */
    List<Employee> selectAll();

    /**
     * 根据id查询一条记录
     *
     * @param id 主键id
     * @return 一行记录
     */
    Employee selectById(Integer id);

    /**
     * 增加一条记录
     * @param employee 增加的值
     * @return 影响行数
     */
    Integer insert(Employee employee);

    /**
     * 根据主键id删除一条数据
     * @param id 主键id
     * @return 影响行数
     */
    Integer delete(Integer id);

    /**
     * 更新一条记录
     * @param employee 更新的数据
     * @return 影响行数
     */
    Integer update(Employee employee);
}
