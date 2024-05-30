package com.atguigu.senior.dao;

public interface BankDao {
    public int addMoney(Integer id, Integer money);

    public int subMoney(Integer id, Integer money);
}
