package com.annet.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: jh
 * @Date: 2019/9/5 11:14
 * @Version: 1.0
 * @Description: 网址重定向处理
 */
@Controller
@Slf4j
public class UrlConversionController {


    private static final String URL = "http://192.168.0.75:8090/booking-system/index.html";

    //url重定向
    @RequestMapping(value = "/bespoke")
    public String getUrl(String idType, String id, String hospitalName) {
        System.out.println(id+"  "+idType);
        String newUrl = URL;
        log.info(newUrl);
        return "redirect:" + newUrl;
    }

}
