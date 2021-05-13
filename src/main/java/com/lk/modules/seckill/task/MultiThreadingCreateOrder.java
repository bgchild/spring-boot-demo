package com.lk.modules.seckill.task;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lk.modules.seckill.domain.SeckillGoods;
import com.lk.modules.seckill.domain.SeckillOrder;
import com.lk.modules.seckill.domain.SeckillStatus;
import com.lk.modules.seckill.mapper.SeckillGoodsMapper;
import com.lk.utils.RedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MultiThreadingCreateOrder {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    SeckillGoodsMapper seckillGoodsMapper;

    @Async
    public void createOrder() {
        try {
            //Thread.sleep(50000);
            String seckillStatusStr = redisTemplate.rpop("SeckillOrderQueue");
            if (StringUtils.isNotBlank(seckillStatusStr)) {
                SeckillStatus seckillStatus = JSONObject.parseObject(seckillStatusStr, SeckillStatus.class);
                System.out.println("Async准备执行....");
                //时间区间
                String time = seckillStatus.getTime();
                //用户登录名
                String username = seckillStatus.getUsername();
                //用户抢购商品
                Long id = seckillStatus.getGoodsId();

                //如果没有库存，则直接抛出异常
                Long count = redisTemplate.hincrBy("SeckillGoodsCount_" + time, String.valueOf(id), -1L);
                if (count < 0) {
                    redisTemplate.hincrBy("SeckillGoodsCount_" + time, String.valueOf(id), 1L);
                    throw new RuntimeException("已售罄!");
                }
                //获取商品数据
                String goodsStr = redisTemplate.hget("SeckillGoods_" + time, String.valueOf(id));
                SeckillGoods goods = JSONObject.parseObject(goodsStr, SeckillGoods.class);
                //如果有库存，则创建秒杀商品订单
                SeckillOrder seckillOrder = new SeckillOrder();
                seckillOrder.setId(IdWorker.getId());
                seckillOrder.setSeckillId(Long.valueOf(id));
                seckillOrder.setMoney(goods.getCostPrice());
                seckillOrder.setUserId(username);
                seckillOrder.setSellerId(goods.getSellerId());
                seckillOrder.setCreateTime(new Date());
                seckillOrder.setStatus("0");
                //将秒杀订单存入到Redis中
                redisTemplate.hset("SeckillOrder", username, seckillOrder);
                //库存减少

                //判断当前商品是否还有库存
                // final Long kc = redisTemplate.hincrBy("SeckillGoodsCount_" + time, String.valueOf(id), -1L);
                goods.setStockCount(goods.getStockCount() - 1);
                if (count <= 0) {
                    //并且将商品数据同步到MySQL中
                    goods.setStockCount(0);
                    seckillGoodsMapper.updateById(goods);
                    //如果没有库存,则清空Redis缓存中该商品
                    redisTemplate.hdel("SeckillGoods_" + time, String.valueOf(id));
                } else {
                    //如果有库存，则直数据重置到Reids中
                    redisTemplate.hset("SeckillGoods_" + time, id, goods);
                }

                //排队成功，更新抢单状态排队支付
                seckillStatus.setStatus(2);
                seckillStatus.setOrderId(seckillOrder.getId());
                seckillStatus.setMoney(seckillOrder.getMoney().floatValue());
                redisTemplate.hset("UserQueenStatus", username, seckillStatus);
                //发送消息
                System.out.println("发送消息");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
