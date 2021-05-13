package com.lk.modules.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("seckill")
public class RedirectController {
    /****
     * 登录地址跳转方法
     * referer是header的一部分，
     * * 当浏览器向web服务器发送请求的时候，一般会带上Referer，告诉服务器我是从哪个页面链接过来的。
     * * 登录地址返回
     * * @param referer
     * * @return
     * */
    @RequestMapping(value = "/back")
    public String redirect(@RequestHeader(value = "Referer", required = false) String referer) {
        if (!StringUtils.isEmpty(referer)) {
            return "redirect:" + referer;
        }
        return "redirect:/";
    }
}
