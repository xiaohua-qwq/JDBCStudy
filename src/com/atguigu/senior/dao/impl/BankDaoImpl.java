package com.atguigu.senior.dao.impl;

import com.atguigu.senior.dao.BankDao;
import com.atguigu.senior.dao.baseDao;

public class BankDaoImpl extends baseDao implements BankDao {
    @Override
    public int addMoney(Integer id, Integer money) {
        String sql = "update t_bank set money = money + ? where id = ?";
        return this.executeUpdate(sql, money, id);
    }

    @Override
    public int subMoney(Integer id, Integer money) {
        String sql = "update t_bank set money = money - ? where id = ?";
        return this.executeUpdate(sql, money, id);
    }
}
