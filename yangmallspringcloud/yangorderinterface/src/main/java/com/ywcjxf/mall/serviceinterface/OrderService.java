package com.ywcjxf.mall.serviceinterface;

import com.ywcjxf.mall.pojo.Order;
import com.ywcjxf.mall.pojo.OrderItemSpec;
import com.ywcjxf.mall.pojo.User;
import com.ywcjxf.mall.serviceinterface.dto.PairDto;
import com.ywcjxf.mall.serviceinterface.dto.SimpleCartDto;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Map<Integer,String> decrInventoryAndSend(Map<Integer,Integer> map, User user);
    //void receiveAndDecrInventory(SimpleCartDto cartDto);
    void test();
    PairDto<Order,List<OrderItemSpec>,String> getOrder(User user);
    void insert(List<List<String>> lists,Integer id);
    Integer validateAddressAndInsert(String trueName,String cityId,
                                     String areaId,String areaInfo,String address,String mobPhone);
    Map<Integer,String> submitOrder(Map<Integer,Integer> map,User user,Integer addressId,String token,String orderId);
}
