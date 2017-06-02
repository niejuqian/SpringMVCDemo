package com.example.aop;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/6/1.
 */
public class Test implements InitializingBean,DisposableBean{
    private long startTime;
    private String name;
    private String money;

    private Collection<String> persons;

    public void beforeSay(){
        System.out.println("===========before");
        startTime = System.currentTimeMillis();
    }

    public void afterSay(){
        System.out.println("===========after");
        System.out.println("本次请求共耗时：" + (System.currentTimeMillis() - startTime));
    }

    @Override
    public void destroy() throws Exception {
        //销毁
        System.out.println("===========destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化
        System.out.println("===========afterPropertiesSet====" + persons);
        if (null != persons) {
            Iterator<String> ite = persons.iterator();
            while (ite.hasNext()) {
                String str = ite.next();
                System.out.println("=====================" + str);
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<String> getPersons() {
        return persons;
    }

    public void setPersons(Collection<String> persons) {
        this.persons = persons;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
