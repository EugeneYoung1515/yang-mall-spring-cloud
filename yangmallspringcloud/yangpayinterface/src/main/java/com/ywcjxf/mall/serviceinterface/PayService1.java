package com.ywcjxf.mall.serviceinterface;

import com.ywcjxf.mall.pojo.Order;
import com.ywcjxf.mall.pojo.User;
import com.ywcjxf.mall.serviceinterface.dto.PairDto;

import java.util.Map;

public interface PayService1 {
    Order orderIdAndAmount(Integer userId);
    Map<String,String> showCode(Integer userId, String method, Long orderId);
    void payNotify(String aoId,Long orderId,String payPrice,String payTime,
                   String detail,String more/*,User user*/);
    boolean paySuccessIsTrue(String aoId,Integer userId);

}
