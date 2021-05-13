package com.lk.modules.seckill.task;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lk.modules.seckill.domain.SeckillGoods;
import com.lk.modules.seckill.mapper.SeckillGoodsMapper;
import com.lk.utils.RedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.List;

@Component
public class SeckillGoodsTask {

    @Autowired
    SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    RedisTemplate redisTemplate;

    @Scheduled(cron = "0/30 * * * * ?")
    public void functionName() {
        final QueryWrapper<SeckillGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "1");
        queryWrapper.gt("stock_count", 0);
        final List<SeckillGoods> seckillGoods = seckillGoodsMapper.selectList(queryWrapper);
        for (SeckillGoods seckillGood : seckillGoods) {
            final Date startTime = seckillGood.getStartTime();
            String key = "SeckillGoods_" + DateUtil.format(startTime, "yyyyMMddHH");
            if (!redisTemplate.hexsit(key, seckillGood.getId())) {
                redisTemplate.hset(key, seckillGood.getId(), seckillGood);
                redisTemplate.hset("SeckillGoodsCount_" + DateUtil.format(startTime, "yyyyMMddHH"), seckillGood.getId(), seckillGood.getStockCount());
            }
        }
        System.out.println("30s缓存...");
    }
}
