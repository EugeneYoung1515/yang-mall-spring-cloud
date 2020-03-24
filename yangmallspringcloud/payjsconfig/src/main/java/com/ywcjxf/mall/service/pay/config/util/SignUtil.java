package com.ywcjxf.mall.service.pay.config.util;

import org.springframework.util.DigestUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SignUtil {

    public static String sign(Map<String, String> map, String privateKey) {
        Collection<String> keyset = map.keySet();
        //List<String> keyList= new ArrayList<>(keyset);
        //Collections.sort(keyList);
        StringBuilder sb = new StringBuilder();
        /*
        for (String key : keyList){
            sb.append(key).append("=").append(map.get(key)).append("&");
        }
        sb.append("key=").append(privateKey);
        */

        /*
        for (String value:map.values()){
            sb.append(value);
        }
        */

        map.forEach((k, v) -> {
            sb.append(v);
        });
        sb.append(privateKey);
        return DigestUtils.md5DigestAsHex(sb.toString().getBytes());
    }

    public static String sign(List<String> list, String privateKey) {
         StringBuilder sb = new StringBuilder();

         for(String s:list){
             sb.append(s);
         }

        sb.append(privateKey);
        return DigestUtils.md5DigestAsHex(sb.toString().getBytes());
    }

}