package com.ywcjxf.mall.serviceinterface;

import com.ywcjxf.mall.pojo.Spec;
import com.ywcjxf.mall.pojo.User;
import com.ywcjxf.mall.serviceinterface.dto.PairDto;

import java.util.List;
import java.util.Map;

public interface CartService {
    PairDto<Boolean,Integer,String> addItemToRedis(User user, boolean flag, String cookieValue, Integer specId, Integer quantity);
    boolean cookieToRedis(User user,String cookieValue,boolean exceptionStatus);
    PairDto<String,Integer,String> addItemToCookie(boolean flag,String cookieValue,Integer specId,Integer quantity);
    Spec getSpec(Integer specId);//这里的spec其实和上面的goods是一样的
    int showCartGoodsNumFromRedis(User user);
    int showCartGoodsNumFromCookie(String cookieValue);
    PairDto<Map<Spec,Integer>,Map<Integer,String>,Object> showSpecWithItem(User user);
    PairDto<Spec,String,String> changeQuantity(User user,Integer specId,Integer quantity);
    Integer delSpec(User user,Integer specId);

    //在这里 item goods spec 是一样的

    Map<Integer,String> checkQuantityBatch(List<Integer> specIds,Map<Integer,Integer> map);
}
