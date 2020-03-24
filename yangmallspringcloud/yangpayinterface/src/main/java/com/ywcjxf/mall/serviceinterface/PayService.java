package com.ywcjxf.mall.serviceinterface;

import com.ywcjxf.mall.pojo.Order;
import com.ywcjxf.mall.pojo.User;
import com.ywcjxf.mall.serviceinterface.dto.PairDto;

public interface PayService {
    Order orderIdAndAmount(User user);
    PairDto<Order,PairDto<String,String,String>,Object> showCode(User user, String method, Long orderId);
    void payNotify(String aoId,Long orderId,String payPrice,String payTime,
                          String detail,String more/*,User user*/);
    boolean paySuccessIsTrue(String aoId,User user);
}
