package com.example.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/5/31.
 * 账号信息
 */
public class AccountInfo implements Serializable{
    private BigDecimal total;
    private BigDecimal balance;

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "total=" + total +
                ", balance=" + balance +
                '}';
    }
}
