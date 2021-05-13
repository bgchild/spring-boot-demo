package com.lk.modules.seckill.service;

import com.lk.modules.seckill.domain.SeckillGoods;

public interface SeckillGoodsService {
    /**
     * 获取详情
     *
     * @param time
     * @param id
     * @return
     */
    SeckillGoods one(String time, String id);
}
