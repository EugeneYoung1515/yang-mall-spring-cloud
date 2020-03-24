package com.ywcjxf.mall.serviceinterface;

import com.ywcjxf.mall.pojo.Order;
import com.ywcjxf.mall.pojo.OrderItemSpec;
import com.ywcjxf.mall.pojo.User;
import com.ywcjxf.mall.serviceinterface.dto.ItemQuantityAndUser;
import com.ywcjxf.mall.serviceinterface.dto.PairDto;

import java.util.List;
import java.util.Map;

public interface OrderService1 {
    Map<Integer,String> decrInventoryAndSend(ItemQuantityAndUser itemQuantityAndUser);
    //void receiveAndDecrInventory(SimpleCartDto cartDto);
    void test();
    Map<String,String> getOrder(Integer userId);
    void insert(List<List<String>> lists, Integer id);
    Integer validateAddressAndInsert(String trueName,String cityId,
                                     String areaId,String areaInfo,String address,String mobPhone);
    Map<Integer,String> submitOrder(ItemQuantityAndUser itemQuantityAndUser,Integer addressId,String token,String orderId);

}
