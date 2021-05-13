package com.lk.modules.seckill.service;

import com.lk.modules.seckill.domain.SeckillStatus;

public interface SeckillOrderService {
    /***
     * 添加秒杀订单
     * @param id:商品ID
     * @param time:商品秒杀开始时间
     * @param username:用户登录名
     * @return
     */
    boolean add(String time, Long id, String username);

    /**
     * 查询抢单状态
     * @param username
     * @return
     */
    SeckillStatus queryStatus(String username);
}
