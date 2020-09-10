package com.annet.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 获取token
 */
@RestController
public class GetToken {

    public static Map<String,String> sessionMap = new HashMap<>();

    @GetMapping("/getToken")
    public String getToken(String id){
        String token = UUID.randomUUID().toString();
        System.out.println(token);
        sessionMap.put(token,id);
        return token;
    }
}
