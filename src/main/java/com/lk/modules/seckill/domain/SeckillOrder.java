package com.lk.modules.seckill.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName tb_seckill_order
 */
@TableName(value ="tb_seckill_order")
@Data
public class SeckillOrder implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 秒杀商品ID
     */
    @TableField(value = "seckill_id")
    private Long seckillId;

    /**
     * 支付金额
     */
    @TableField(value = "money")
    private BigDecimal money;

    /**
     * 用户
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 商家
     */
    @TableField(value = "seller_id")
    private String sellerId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 支付时间
     */
    @TableField(value = "pay_time")
    private Date payTime;

    /**
     * 状态，0未支付，1已支付
     */
    @TableField(value = "status")
    private String status;

    /**
     * 收货人地址
     */
    @TableField(value = "receiver_address")
    private String receiverAddress;

    /**
     * 收货人电话
     */
    @TableField(value = "receiver_mobile")
    private String receiverMobile;

    /**
     * 收货人
     */
    @TableField(value = "receiver")
    private String receiver;

    /**
     * 交易流水
     */
    @TableField(value = "transaction_id")
    private String transactionId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}