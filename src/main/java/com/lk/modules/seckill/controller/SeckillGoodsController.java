package com.lk.modules.seckill.controller;

import com.lk.modules.seckill.domain.SeckillGoods;
import com.lk.modules.seckill.domain.SeckillStatus;
import com.lk.modules.seckill.service.SeckillGoodsService;
import com.lk.modules.seckill.service.SeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("seckill")
public class SeckillGoodsController {
    @Autowired
    SeckillGoodsService seckillGoodsService;
    @Autowired
    SeckillOrderService seckillOrderService;

    @RequestMapping("/getOne")
    @ResponseBody
    public SeckillGoods getOne(@RequestParam("time") String time, @RequestParam("id") String id) {
        return seckillGoodsService.one(time, id);
    }

    @RequestMapping("/add")
    @ResponseBody
    public boolean add(@RequestParam("time") String time, @RequestParam("id") String id, @RequestParam("username") String username) {
        return seckillOrderService.add(time, Long.valueOf(id), username);
    }

    @RequestMapping("/queryStatus")
    @ResponseBody
    public SeckillStatus queryStatus(@RequestParam("username") String username) {
        return seckillOrderService.queryStatus(username);
    }
}
