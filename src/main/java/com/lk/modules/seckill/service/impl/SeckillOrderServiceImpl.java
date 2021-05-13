package com.lk.modules.seckill.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.lk.modules.seckill.domain.SeckillGoods;
import com.lk.modules.seckill.domain.SeckillOrder;
import com.lk.modules.seckill.domain.SeckillStatus;
import com.lk.modules.seckill.mapper.SeckillGoodsMapper;
import com.lk.modules.seckill.service.SeckillOrderService;
import com.lk.modules.seckill.task.MultiThreadingCreateOrder;
import com.lk.utils.RedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {

    @Autowired
    private MultiThreadingCreateOrder multiThreadingCreateOrder;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean add(String time, Long id, String username) {
        //递增，判断是否排队
//        Long userQueueCount = redisTemplate.hincrBy("UserQueueCount", username, 1L);
//        if (userQueueCount > 1) {
//            //100：表示有重复抢单
//            throw new RuntimeException("别贪心，一个人只能买一件");
//        }
        //排队信息封装
        SeckillStatus seckillStatus = new SeckillStatus(username, new Date(), 1, id, time);
        //将秒杀抢单信息存入到Redis中,这里采用List方式存储,List本身是一个队列
        redisTemplate.lpush("SeckillOrderQueue", seckillStatus);
        //记录抢单状态
        redisTemplate.hset("UserQueenStatus", username, seckillStatus);
        //多线程操作
        multiThreadingCreateOrder.createOrder();
        return true;
    }

    @Override
    public SeckillStatus queryStatus(String username) {
        final String userQueenStatus = redisTemplate.hget("UserQueenStatus", username);
        return JSONObject.parseObject(userQueenStatus, SeckillStatus.class);
    }
}
