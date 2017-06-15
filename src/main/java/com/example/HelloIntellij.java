package com.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by Administrator on 2017/6/2.
 */
public class HelloIntellij {
    public void sayHi(){
        System.out.println("==============HelloIntellij");
    }


    public static void main(String[] args) {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(30);
            config.setMaxIdle(10);
            config.setTestOnBorrow(true);
            config.setTestOnReturn(true);
            JedisPool pool = new JedisPool(config,"192.168.30.128",6379,1500,"laonie");
            Jedis jedis = pool.getResource();
            String username = jedis.get("username");
            System.out.println(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
