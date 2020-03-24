package com.ywcjxf.mall.serviceinterface.feign;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ywcjxf.mall.mapper.PairDtoMapper;
import com.ywcjxf.mall.pojo.Order;
import com.ywcjxf.mall.pojo.OrderItemSpec;
import com.ywcjxf.mall.pojo.User;
import com.ywcjxf.mall.serviceinterface.OrderService;
import com.ywcjxf.mall.serviceinterface.OrderService1;
import com.ywcjxf.mall.serviceinterface.dto.ItemQuantityAndUser;
import com.ywcjxf.mall.serviceinterface.dto.PairDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderServiceFeignDecorator implements OrderService{
    private OrderService1 orderService1;

    private PairDtoMapper pairDtoMapper;

    @Override
    public Map<Integer, String> decrInventoryAndSend(Map<Integer, Integer> map, User user) {
        return orderService1.decrInventoryAndSend(new ItemQuantityAndUser(map,user));
    }

    @Override
    public void test() {
        orderService1.test();
    }

    @Override
    public PairDto<Order, List<OrderItemSpec>, String> getOrder(User user) {
        return pairDtoMapper.fromMap(orderService1.getOrder(user.getUserId()),Order.class,new TypeReference<List<OrderItemSpec>>(){},
        String.class);
    }

    @Override
    public void insert(List<List<String>> lists, Integer id) {
        orderService1.insert(lists,id);
    }

    @Override
    public Integer validateAddressAndInsert(String trueName, String cityId, String areaId, String areaInfo, String address, String mobPhone) {
        return orderService1.validateAddressAndInsert(trueName,cityId,areaId,areaInfo,address,mobPhone);
    }

    @Override
    public Map<Integer, String> submitOrder(Map<Integer, Integer> map, User user, Integer addressId, String token, String orderId) {
        return orderService1.submitOrder(new ItemQuantityAndUser(map,user),addressId,token,orderId);
    }

    @Autowired
    public void setOrderService1(OrderService1 orderService1) {
        this.orderService1 = orderService1;
    }

    @Autowired
    public void setPairDtoMapper(PairDtoMapper pairDtoMapper) {
        this.pairDtoMapper = pairDtoMapper;
    }
}
