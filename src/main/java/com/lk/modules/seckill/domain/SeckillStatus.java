package com.lk.modules.seckill.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillStatus {
    public SeckillStatus(String username, Date createTime, int status, Long goodsId, String time) {
        this.username = username;
        this.createTime = createTime;
        this.status = status;
        this.goodsId = goodsId;
        this.time = time;
    }
    //秒杀用户名
    private String username;
    //创建时间
    private Date createTime;
    //秒杀状态 1:排队中，2:秒杀等待支付,3:支付超时，4:秒杀失败,5:支付完成
    private int status;
    //秒杀的商品ID
    private Long goodsId;
    //应付金额
    private Float money;
    //订单号
    private Long orderId;
    //时间段
    private String time;
}
