package com.lk.modules.seckill.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lk.modules.seckill.domain.SeckillGoods;
import com.lk.modules.seckill.service.SeckillGoodsService;
import com.lk.utils.RedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public SeckillGoods one(String time, String id) {
        final String hget = redisTemplate.hget("SeckillGoods_" + time, id);
        return JSONObject.parseObject(hget, SeckillGoods.class);
    }
}
