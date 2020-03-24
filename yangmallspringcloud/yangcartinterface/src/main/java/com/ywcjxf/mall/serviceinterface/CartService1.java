package com.ywcjxf.mall.serviceinterface;

import com.ywcjxf.mall.pojo.Spec;
import com.ywcjxf.mall.pojo.User;
import com.ywcjxf.mall.serviceinterface.dto.PairDto;

import java.util.List;
import java.util.Map;

public interface CartService1 {
    Map<String,String> addItemToRedis(User user, boolean flag, String cookieValue, Integer specId, Integer quantity);
    boolean cookieToRedis(User user,String cookieValue,boolean exceptionStatus);
    Map<String,String> addItemToCookie(boolean flag,String cookieValue,Integer specId,Integer quantity);
    Spec getSpec(Integer specId);
    int showCartGoodsNumFromRedis(Integer userId);
    int showCartGoodsNumFromCookie(String cookieValue);
    Map<String,String> showSpecWithItem(Integer userId);
    Map<String,String> changeQuantity(User user,Integer specId,Integer quantity);
    Integer delSpec(User user,Integer specId);



    Map<Integer,String> checkQuantityBatch(List<Integer> specIds, Map<Integer,Integer> map);

}
