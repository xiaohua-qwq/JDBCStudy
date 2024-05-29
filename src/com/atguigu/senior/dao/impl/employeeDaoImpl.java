package com.atguigu.senior.dao.impl;

import com.atguigu.senior.dao.EmployeeDao;
import com.atguigu.senior.dao.baseDao;
import com.atguigu.senior.pojo.Employee;

import java.util.List;

public class employeeDaoImpl extends baseDao implements EmployeeDao {
    /**
     * 查询数据库中所有的记录
     *
     * @return 所有记录
     */
    @Override
    public List<Employee> selectAll() {
        String sql = "select emp_id as empId,emp_name as empName,emp_salary as empSalary,emp_age as empAge from t_emp";
        return this.executeQuery(Employee.class, sql);
    }

    /**
     * 根据id查询一条记录
     *
     * @param id 主键id
     * @return 一行记录
     */
    @Override
    public Employee selectById(Integer id) {
        return null;
    }

    /**
     * 增加一条记录
     *
     * @param employee 增加的值
     * @return 影响行数
     */
    @Override
    public Integer insert(Employee employee) {
        return null;
    }

    /**
     * 根据主键id删除一条数据
     *
     * @param id 主键id
     * @return 影响行数
     */
    @Override
    public Integer delete(Integer id) {
        return null;
    }

    /**
     * 更新一条记录
     *
     * @param employee 更新的数据
     * @return 影响行数
     */
    @Override
    public Integer update(Employee employee) {
        return null;
    }
}
